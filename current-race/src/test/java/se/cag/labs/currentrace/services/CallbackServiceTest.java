package se.cag.labs.currentrace.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CallbackServiceTest {
    private CallbackService callbackService;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        callbackService = new CallbackService();
        ReflectionTestUtils.setField(callbackService, "restTemplate", restTemplate);
    }

    @Test
    public void givenRestTemplateSuccess_NoException() {
        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        currentRaceStatus.setEvent(RaceStatus.Event.FINISH);
        currentRaceStatus.setStartTime(1L);
        currentRaceStatus.setMiddleTime(1L);
        currentRaceStatus.setFinishTime(1L);
        currentRaceStatus.setRaceId("RACEID");
        currentRaceStatus.setRaceActivatedTime(1L);
        currentRaceStatus.setCallbackUrl("URL");

        try {
            callbackService.reportStatus(currentRaceStatus);
        } catch (Exception e) {
            fail("Should not throw exception");
        }

        verify(restTemplate, times(1)).postForLocation(eq("URL"), any(RaceStatus.class));
    }

    @Test
    public void givenRestTemplateFailure_NoException() {
        when(restTemplate.postForLocation(anyString(), any(RaceStatus.class))).thenThrow(new RestClientException("OHNO"));

        try {
            callbackService.reportStatus(new CurrentRaceStatus());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }
}
