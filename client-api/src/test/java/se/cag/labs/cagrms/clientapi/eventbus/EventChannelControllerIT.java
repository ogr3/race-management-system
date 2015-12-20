package se.cag.labs.cagrms.clientapi.eventbus;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import se.cag.labs.cagrms.clientapi.ClientApiApplication;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ClientApiApplication.class})
// NOTE!! order is important
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class EventChannelControllerIT {
    @Value("${local.server.port}")
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void eventShouldNotAllowOtherThanPOST() {
        when().get("/event").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        when().put("/event").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        when().patch("/event").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        when().delete("/event").then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @Test
    public void eventShouldNotConsumeOtherThanJSON() {
        given().contentType(ContentType.ANY).when().post("/event").then().statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        given().contentType(ContentType.TEXT).when().post("/event").then().statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        given().contentType(ContentType.XML).when().post("/event").then().statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        given().contentType(ContentType.HTML).when().post("/event").then().statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        given().contentType(ContentType.URLENC).when().post("/event").then().statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        given().contentType(ContentType.BINARY).when().post("/event").then().statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    @Test
    public void eventShouldReturnOKIfEventTypeExist() {
        String jsonRequest = "{\"eventType\": \"EVENT\"}";

        given().contentType(ContentType.JSON).body(jsonRequest).when().post("/event").then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void eventShouldReturnBadRequestIfEventTypeNotExist() {
        String jsonRequest = "{\"asd\": \"EVENT\"}";

        given().contentType(ContentType.JSON).body(jsonRequest).when().post("/event").then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void eventShouldReturnInternalServerErrorIfException() {
        String jsonRequest = "{asd: EVENT\"}";

        given().contentType(ContentType.JSON).body(jsonRequest).when().post("/event").then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
