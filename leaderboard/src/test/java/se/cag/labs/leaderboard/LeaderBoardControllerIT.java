package se.cag.labs.leaderboard;

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
import se.cag.labs.usermanager.User;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.*;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {LeaderBoardApplication.class})
// NOTE!! order is important
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("int-test")
public class LeaderBoardControllerIT {
    @Rule
    public MongoDbRule mongoDbRule = new MongoDbRule(mongoDb().databaseName("test").build());
    @Autowired
    private ApplicationContext applicationContext; //Needed by nosqlunit
    @Autowired
    private LeaderBoardRepository repository;
    @Value("${local.server.port}")
    private int port;
    private UserResult userResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        repository.deleteAll();
        RestAssured.port = port;

        userResult = new UserResult();
        userResult.setMiddleTime(2);
        userResult.setTime(3);
        userResult.setResult(ResultType.FINISHED);
        userResult.setUser(new User("NAME", "EMAIL", "PASSWORD"));
    }

    @Test
    public void canNotDeletePutPatch() {
        given().param("userResult", userResult).when().delete("/results").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().param("userResult", userResult).when().put("/results").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().param("userResult", userResult).when().patch("/results").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @Test
    public void canInsertUserResultWhenPOST() {
        given().
                contentType(ContentType.JSON).
                body(userResult).
                    when().post("/results").
                        then().statusCode(HttpStatus.OK.value());

        List<UserResult> result = repository.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());

        UserResult foundUserResult = result.get(0);
        assertNotNull(foundUserResult.getId());
        assertNotNull(foundUserResult.getCreated());
        assertEquals(2, foundUserResult.getMiddleTime());
        assertEquals(3, foundUserResult.getTime());
        assertEquals(ResultType.FINISHED, foundUserResult.getResult());
        assertEquals("NAME", foundUserResult.getUser().getName());
        assertEquals("EMAIL", foundUserResult.getUser().getEmail());
        assertEquals("PASSWORD", foundUserResult.getUser().getPassword());
    }

    @Test
    public void canFindUserResultWhenGET() {
        repository.insert(userResult);

        Response response = get("/results");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<UserResult> userResultList = Arrays.asList(response.getBody().as(UserResult[].class));

        assertNotNull(userResultList);
        assertFalse(userResultList.isEmpty());
        assertEquals(1, userResultList.size());
        UserResult foundUserResult = userResultList.get(0);
        assertEquals(2, foundUserResult.getMiddleTime());
        assertEquals(3, foundUserResult.getTime());
        assertNotNull(foundUserResult.getCreated());
        assertEquals(ResultType.FINISHED, foundUserResult.getResult());

        User user = foundUserResult.getUser();
        assertNotNull(user);
        assertEquals("NAME", user.getName());
        assertEquals("EMAIL", user.getEmail());
        assertEquals("PASSWORD", user.getPassword());
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
            return LeaderBoardRepository.class.getPackage().getName();
        }
    }
}
