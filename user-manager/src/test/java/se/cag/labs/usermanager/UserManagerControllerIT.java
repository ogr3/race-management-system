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

        Response getResponse = when().get("/users");
        validateGetUsersResponse(getResponse);

        Response postResponse = when().post("/users");
        validateGetUsersResponse(postResponse);

        Response patchResponse = when().patch("/users");
        validateGetUsersResponse(patchResponse);

        Response deleteResponse = when().delete("/users");
        validateGetUsersResponse(deleteResponse);

        Response putResponse = when().put("/users");
        validateGetUsersResponse(putResponse);
    }

    private void validateGetUsersResponse(Response response){
        assertEquals(HttpStatus.OK.value(), response.statusCode());

        List<User> userList = Arrays.asList(response.body().as(User[].class));
        assertNotNull(userList);
        assertEquals(1, userList.size());
        User foundUser = userList.get(0);

        assertEquals("NAME", foundUser.getId());
        assertEquals("EMAIL", foundUser.getUserId());
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

        Token token = response.getBody().as(Token.class);
        assertNotNull(token);

        Session session = sessionRepository.findByToken(token.getToken());
        assertNotNull(session);

        User user = userRepository.findByUserId("NAME");
        assertNotNull(user);
        assertEquals(user.getId(), session.getUserId());
    }

    @Test
    public void getUserForToken() {
        given().contentType(ContentType.JSON).body(new Token("")).when().get("/getUserForToken").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).body(new Token("")).when().delete("/getUserForToken").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).body(new Token("")).when().put("/getUserForToken").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).body(new Token("")).when().patch("/getUserForToken").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

        Token token = new Token("TOKEN");

        Session session = new Session();
        session.setToken("TOKEN");
        session.setUserId("USERID");
        session.setTimeout(LocalDateTime.now().plusDays(1));
        sessionRepository.save(session);

        user.setId("USERID");
        userRepository.save(user);

        Response response = given().contentType(ContentType.JSON).body(token).when().post("/getUserForToken");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        User foundUser = response.getBody().as(User.class);

        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getUserId(), foundUser.getUserId());
        assertEquals("", foundUser.getPassword());
    }

    @Test
    public void registerNewUser() {
        given().contentType(ContentType.JSON).body(new NewUser()).when().get("/registerNewUser").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).body(new NewUser()).when().delete("/registerNewUser").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).body(new NewUser()).when().put("/registerNewUser").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).body(new NewUser()).when().patch("/registerNewUser").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());

        NewUser newUser = new NewUser();
        newUser.setUserId("USERNAME");
        newUser.setPassword("PASSWORD");

        given().contentType(ContentType.JSON).body(newUser).when().post("/registerNewUser").then().statusCode(HttpStatus.CREATED.value());

        User result = userRepository.findByUserId(newUser.getUserId());
        assertNotNull(result);
        assertEquals(newUser.getUserId(), result.getUserId());
        assertEquals(newUser.getPassword(), result.getPassword());
    }
}
