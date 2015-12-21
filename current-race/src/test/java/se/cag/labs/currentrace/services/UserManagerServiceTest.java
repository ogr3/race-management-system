package se.cag.labs.currentrace.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import se.cag.labs.currentrace.apicontroller.apimodel.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserManagerServiceTest {
    private UserManagerService userManagerService;
    @Mock
    private RestTemplate restTemplate;
    private ParameterizedTypeReference<List<User>> typeReference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userManagerService = new UserManagerService();
        ReflectionTestUtils.setField(userManagerService, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(userManagerService, "userManagerBaseUri", "MOCKURL");
        typeReference = new ParameterizedTypeReference<List<User>>() {};
    }

    @Test
    public void givenRestTemplateExchangeSuccessfull_ExpectFilledArray() {
        User user = User.builder().name("nisse").id("id").password("password").build();
        ResponseEntity<List<User>> responseEntity = new ResponseEntity<>(Arrays.asList(user), HttpStatus.OK);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                eq(typeReference)
        )).thenReturn(responseEntity);

        List<User> result = userManagerService.getUsers();

        verify(restTemplate, times(1)).exchange(
                "MOCKURL/users",
                HttpMethod.GET,
                null,
                typeReference
        );
        assertEquals(responseEntity.getBody(), result);
        User foundUser = responseEntity.getBody().get(0);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getPassword(), foundUser.getPassword());
    }

    @Test
    public void givenRestTemplateExchangeException_ExpectEmptyArray() {
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                eq(typeReference)
        )).thenThrow(new RestClientException("In closing, go away."));

        List<User> result = userManagerService.getUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
