package se.cag.labs.usermanager;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {UserManagerApplication.class})
// NOTE!! order is important
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserManagerControllerIT {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;
    @Value("${local.server.port}")
    private int port;
    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        sessionRepository.deleteAll();
        userRepository.deleteAll();
        RestAssured.port = port;
        user = new User("NAME", "EMAIL", "PASSWORD");
    }

    @Test
    public void getUsers() {
        userRepository.save(user);

        Response getResponse = when().put("/users");

        assertEquals(HttpStatus.OK.value(), getResponse.statusCode());

        List<User> userList = Arrays.asList(getResponse.body().as(User[].class));
        assertNotNull(userList);
        assertEquals(1, userList.size());
        User foundUser = userList.get(0);

        assertEquals("NAME", foundUser.getUserId());
        assertEquals("EMAIL", foundUser.getDisplayName());
        assertEquals("", foundUser.getPassword());

    }

    @Test
    public void login() {
        given().contentType(ContentType.JSON).body(new NewUser()).when().get("/login").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).body(new NewUser()).when().delete("/login").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).body(new NewUser()).when().put("/login").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).body(new NewUser()).when().patch("/login").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

        NewUser newUser = new NewUser();
        newUser.setUserId("NAME");
        newUser.setPassword("PASSWORD");

        userRepository.save(user);

        Response response = given().contentType(ContentType.JSON).body(newUser).when().post("/login");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        UserInfo token = response.getBody().as(UserInfo.class);
        assertNotNull(token);

        User user = userRepository.findByUserId("NAME");
        assertNotNull("User should not be null", user);
    }

    @Test
    public void getUserForToken() {
        Token token = new Token("TOKEN");

        Session session = new Session();
        session.setToken("{\"token\":\"TOKEN\"}");
        session.setUserId("USERID");
        session.setTimeout(LocalDateTime.now().plusDays(1));
        sessionRepository.save(session);

        user.setId("USERID");
        userRepository.save(user);

        Response response = given().param("token", token).when().get("/users");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        User foundUser = response.getBody().as(User.class);

        assertEquals(user.getUserId(), foundUser.getUserId());
        assertEquals("", foundUser.getPassword());
    }

    @Test
    public void registerNewUser() {
        NewUser newUser = new NewUser();
        newUser.setUserId("USERNAME");
        newUser.setPassword("PASSWORD");

        given().contentType(ContentType.JSON).body(newUser).when().post("/users").then().statusCode(HttpStatus.CREATED.value());

        User result = userRepository.findByUserId(newUser.getUserId());
        assertNotNull(result);
        assertEquals(newUser.getUserId(), result.getUserId());
        assertEquals(newUser.getPassword(), result.getPassword());
    }
}
