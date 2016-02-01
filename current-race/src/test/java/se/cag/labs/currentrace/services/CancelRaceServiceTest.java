package se.cag.labs.currentrace.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class CancelRaceServiceTest {
    @InjectMocks
    private CancelRaceService cancelRaceService;
    @Mock
    private CurrentRaceRepository repository;
    @Mock
    private CallbackService callbackService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenCurrentRaceNull_ExpectStatusNOT_FOUND() {
        CancelRaceService.ReturnStatus result = cancelRaceService.cancelRace();

        assertNotNull(result);
        assertEquals(CancelRaceService.ReturnStatus.NOT_FOUND, result);

        verify(repository, never()).save(any(CurrentRaceStatus.class));
        verify(callbackService, never()).reportStatus(any(CurrentRaceStatus.class));
    }

    @Test
    public void givenCurrentRaceNotNull_ExpectStatusACCEPTED() {
        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        currentRaceStatus.setEvent(RaceStatus.Event.FINISH);
        currentRaceStatus.setStartTime(1L);
        currentRaceStatus.setSplitTime(1L);
        currentRaceStatus.setFinishTime(1L);
        currentRaceStatus.setRaceId("RACEID");
        currentRaceStatus.setRaceActivatedTime(1L);
        currentRaceStatus.setCallbackUrl("URL");

        when(repository.findByRaceId(anyString())).thenReturn(currentRaceStatus);

        CancelRaceService.ReturnStatus result = cancelRaceService.cancelRace();

        assertNotNull(result);
        assertEquals(CancelRaceService.ReturnStatus.ACCEPTED, result);

        ArgumentCaptor<CurrentRaceStatus> argument = ArgumentCaptor.forClass(CurrentRaceStatus.class);

        verify(repository, times(1)).save(argument.capture());
        assertEquals(RaceStatus.State.INACTIVE, argument.getValue().getState());
        assertNull(argument.getValue().getEvent());
        assertNull(argument.getValue().getStartTime());
        assertNull(argument.getValue().getSplitTime());
        assertNull(argument.getValue().getFinishTime());
        assertEquals("RACEID", argument.getValue().getRaceId());
        assertNull(argument.getValue().getRaceActivatedTime());
        assertEquals("URL", argument.getValue().getCallbackUrl());

        verify(callbackService, times(1)).reportStatus(argument.capture());
        assertEquals(RaceStatus.State.INACTIVE, argument.getValue().getState());
        assertNull(argument.getValue().getEvent());
        assertNull(argument.getValue().getStartTime());
        assertNull(argument.getValue().getSplitTime());
        assertNull(argument.getValue().getFinishTime());
        assertEquals("RACEID", argument.getValue().getRaceId());
        assertNull(argument.getValue().getRaceActivatedTime());
        assertEquals("URL", argument.getValue().getCallbackUrl());
    }
}
