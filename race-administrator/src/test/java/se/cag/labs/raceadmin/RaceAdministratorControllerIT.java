package se.cag.labs.raceadmin;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import se.cag.labs.raceadmin.peerservices.ClientApiService;
import se.cag.labs.raceadmin.peerservices.CurrentRaceService;

import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RaceAdministratorApplication.class})
// NOTE!! order is important
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestPropertySource(locations = "classpath:application-test.properties")
public class RaceAdministratorControllerIT {
    @Autowired
    private ActiveRaceRepository activeRaceRepository;
    @Autowired
    private UserQueueRepository userQueueRepository;
    @Value("${local.server.port}")
    private int port;
    private User user;
    @Mock
    private CurrentRaceService currentRaceService;
    @Autowired
    private RaceAdministratorController raceAdministratorController;
    @Autowired
    private ClientApiService clientApiService;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(raceAdministratorController, "currentRaceService", currentRaceService);
        ReflectionTestUtils.setField(clientApiService, "restTemplate", restTemplate);
        activeRaceRepository.deleteAll();
        userQueueRepository.deleteAll();
        RestAssured.port = port;
        user = new User();
        user.setUserId("NAME");
        user.setDisplayName("EMAIL");
    }

    @Test
    public void registerForRace() {
        given().body(user).when().put("/userqueue").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().body(user).when().patch("/userqueue").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().body(user).when().delete("/userqueue").then().statusCode(HttpStatus.BAD_REQUEST.value());

        given().contentType(ContentType.JSON).body(user).post("/userqueue").then().statusCode(HttpStatus.OK.value());

        verify(currentRaceService, atLeastOnce()).startRace();

        List<User> userQueueResult = userQueueRepository.findAll();
        assertNotNull(userQueueResult);
        assertEquals(1, userQueueResult.size());

        List<RaceStatus> activeRaceRepositoryResult = activeRaceRepository.findAll();
        assertNotNull(activeRaceRepositoryResult);
        assertEquals(1, activeRaceRepositoryResult.size());

        RaceStatus raceStatus = activeRaceRepositoryResult.get(0);
        assertEquals("NAME", raceStatus.getUser().getUserId());
        assertEquals("EMAIL", raceStatus.getUser().getDisplayName());
    }

    @Test
    public void getQueue() {
        userQueueRepository.save(user);

        Response response = when().get("userqueue");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        List<User> userList = Arrays.asList(response.body().as(User[].class));

        assertNotNull(userList);
        assertEquals(1, userList.size());

        User foundUser = userList.get(0);
        assertEquals("NAME", foundUser.getDisplayName());
        assertEquals("EMAIL", foundUser.getUserId());
    }

    @Test
    public void onRaceStatusUpdate() {
        RaceStatus raceStatus = new RaceStatus(user);
        raceStatus.setState(RaceStatus.RaceState.ACTIVE);
        raceStatus.setStartTime(1L);
        raceStatus.setSplitTime(2L);
        raceStatus.setFinishTime(3L);
        raceStatus.setEvent(RaceStatus.RaceEvent.START);

        activeRaceRepository.save(raceStatus);

        given().contentType(ContentType.JSON).body(raceStatus).
                when().post("/onracestatusupdate").then().statusCode(HttpStatus.OK.value());

        List<RaceStatus> activeRaceList = activeRaceRepository.findAll();
        assertNotNull(activeRaceList);
        assertEquals(1, activeRaceList.size());

        RaceStatus foundRaceStatus = activeRaceList.get(0);

        assertEquals(new Long(1), foundRaceStatus.getStartTime());
        assertEquals(new Long(2), foundRaceStatus.getSplitTime());
        assertEquals(new Long(3), foundRaceStatus.getFinishTime());
        assertEquals(user.getDisplayName(), foundRaceStatus.getUser().getDisplayName());
        assertEquals(user.getUserId(), foundRaceStatus.getUser().getUserId());
        assertEquals(RaceStatus.RaceEvent.START, foundRaceStatus.getEvent());
        assertEquals(RaceStatus.RaceState.ACTIVE, foundRaceStatus.getState());
    }

}
