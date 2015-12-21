package se.cag.labs.currentrace.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PassageDetectedServiceTest {
    @InjectMocks
    private PassageDetectedService passageDetectedService;
    @Mock
    private CurrentRaceRepository repository;
    @Mock
    private CallbackService callbackService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenSensorIdNotStartSplitOrFinish_ExpectERROR() {
        PassageDetectedService.ReturnStatus result = passageDetectedService.passageDetected("FEL", 1L);

        assertNotNull(result);
        assertEquals(PassageDetectedService.ReturnStatus.ERROR, result);

        verify(repository, times(1)).findByRaceId(anyString());
        verify(repository, never()).save(any(CurrentRaceStatus.class));
        verify(callbackService, never()).reportStatus(any(CurrentRaceStatus.class));
    }

    @Test
    public void givenCurrentRaceStateINACTIVE_ExpectStatusIGNORED() {
        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.INACTIVE);

        when(repository.findByRaceId(anyString())).thenReturn(currentRaceStatus);
        PassageDetectedService.ReturnStatus result = passageDetectedService.passageDetected("START", 1L);

        assertNotNull(result);
        assertEquals(PassageDetectedService.ReturnStatus.IGNORED, result);

        verify(repository, times(1)).findByRaceId(anyString());
        verify(repository, never()).save(any(CurrentRaceStatus.class));
        verify(callbackService, never()).reportStatus(any(CurrentRaceStatus.class));
    }

    @Test
    public void givenCurrentRaceStateACTIVE_ExpectStatusACCEPTED() {
        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);

        when(repository.findByRaceId(anyString())).thenReturn(currentRaceStatus);
        PassageDetectedService.ReturnStatus result = passageDetectedService.passageDetected("START", 1L);

        assertNotNull(result);
        assertEquals(PassageDetectedService.ReturnStatus.ACCEPTED, result);

        verify(repository, times(1)).findByRaceId(anyString());
        verify(repository, times(1)).save(any(CurrentRaceStatus.class));
        verify(callbackService, times(1)).reportStatus(any(CurrentRaceStatus.class));
    }
}
