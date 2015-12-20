package se.cag.labs.raceadmin;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import se.cag.labs.raceadmin.services.CurrentRaceService;
import se.cag.labs.usermanager.User;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("int-test")
public class RaceAdministratorControllerIT {
    @Rule
    public MongoDbRule mongoDbRule = new MongoDbRule(mongoDb().databaseName("test").build());
    @Autowired
    private ApplicationContext applicationContext; //Needed by nosqlunit
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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(raceAdministratorController, "currentRaceService", currentRaceService);
        activeRaceRepository.deleteAll();
        userQueueRepository.deleteAll();
        RestAssured.port = port;
        user = new User();
        user.setName("NAME");
        user.setEmail("EMAIL");
        user.setPassword("PASSWORD");
    }

    @Test
    public void registerForRace() {
        given().body(user).when().put("/userqueue").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().body(user).when().patch("/userqueue").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().body(user).when().delete("/userqueue").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

        given().contentType(ContentType.JSON).body(user).post("/userqueue").then().statusCode(HttpStatus.OK.value());

        verify(currentRaceService, atLeastOnce()).startRace();

        List<User> userQueueResult = userQueueRepository.findAll();
        assertNotNull(userQueueResult);
        assertEquals(1, userQueueResult.size());

        List<RaceStatus> activeRaceRepositoryResult = activeRaceRepository.findAll();
        assertNotNull(activeRaceRepositoryResult);
        assertEquals(1, activeRaceRepositoryResult.size());

        RaceStatus raceStatus = activeRaceRepositoryResult.get(0);
        assertEquals("NAME", raceStatus.getUser().getName());
        assertEquals("EMAIL", raceStatus.getUser().getEmail());
        assertEquals("PASSWORD", raceStatus.getUser().getPassword());
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
        assertEquals("NAME", foundUser.getName());
        assertEquals("EMAIL", foundUser.getEmail());
        assertEquals("PASSWORD", foundUser.getPassword());
    }

    @Test
    public void onRaceStatusUpdate() {
        RaceStatus raceStatus = new RaceStatus(user);
        raceStatus.setState(RaceStatus.RaceState.ACTIVE);
        raceStatus.setStartTime(1L);
        raceStatus.setMiddleTime(2L);
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
        assertEquals(new Long(2), foundRaceStatus.getMiddleTime());
        assertEquals(new Long(3), foundRaceStatus.getFinishTime());
        assertEquals(user.getName(), foundRaceStatus.getUser().getName());
        assertEquals(user.getEmail(), foundRaceStatus.getUser().getEmail());
        assertEquals(user.getPassword(), foundRaceStatus.getUser().getPassword());
        assertEquals(RaceStatus.RaceEvent.START, foundRaceStatus.getEvent());
        assertEquals(RaceStatus.RaceState.ACTIVE, foundRaceStatus.getState());
    }

    @Configuration
    @EnableMongoRepositories
    @Profile("int-test")
    static class MongoConfiguration extends AbstractMongoConfiguration {
        @Value("${spring.data.mongodb.uri}")
        String mongoUri;

        @Override
        protected String getDatabaseName() {
            return "test";
        }

        @Bean
        @Override
        public Mongo mongo() throws UnknownHostException {
            return new MongoClient(new MongoClientURI(mongoUri));
        }

        @Override
        protected String getMappingBasePackage() {
            return ActiveRaceRepository.class.getPackage().getName();
        }
    }
}
