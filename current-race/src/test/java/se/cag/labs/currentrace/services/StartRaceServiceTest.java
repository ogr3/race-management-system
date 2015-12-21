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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class StartRaceServiceTest {
    @InjectMocks
    private StartRaceService startRaceService;
    @Mock
    private CurrentRaceRepository repository;
    @Mock
    private TimerService timerService;
    private String callbackUrl;
    private ArgumentCaptor<CurrentRaceStatus> argument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        callbackUrl = "URL";
        argument = ArgumentCaptor.forClass(CurrentRaceStatus.class);
    }

    @Test
    public void givenCurrentRaceNull_ExpectStatusStarted() {
        when(repository.findByRaceId(anyString())).thenReturn(null);

        StartRaceService.ReturnStatus result = startRaceService.startRace(callbackUrl);

        assertNotNull(result);
        assertEquals(StartRaceService.ReturnStatus.STARTED, result);

        verify(repository, times(1)).findByRaceId(anyString());
        verify(timerService, times(1)).startTimer();
        verify(repository, times(1)).save(argument.capture());

        assertEquals(callbackUrl, argument.getValue().getCallbackUrl());
        assertEquals(RaceStatus.State.ACTIVE, argument.getValue().getState());
        assertNotNull(argument.getValue().getRaceActivatedTime());
    }

    @Test
    public void givenCurrentRaceINACTIVE_ExpectStatusStarted() {
        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.INACTIVE);
        when(repository.findByRaceId(anyString())).thenReturn(currentRaceStatus);

        StartRaceService.ReturnStatus result = startRaceService.startRace(callbackUrl);

        assertNotNull(result);
        assertEquals(StartRaceService.ReturnStatus.STARTED, result);

        verify(repository, times(1)).findByRaceId(anyString());
        verify(timerService, times(1)).startTimer();
        verify(repository, times(1)).save(argument.capture());

        assertEquals(callbackUrl, argument.getValue().getCallbackUrl());
        assertEquals(RaceStatus.Event.NONE, argument.getValue().getEvent());
        assertEquals(RaceStatus.State.ACTIVE, argument.getValue().getState());
        assertNotNull(argument.getValue().getRaceActivatedTime());
        assertNull(argument.getValue().getStartTime());
        assertNull(argument.getValue().getMiddleTime());
        assertNull(argument.getValue().getFinishTime());
    }

    @Test
    public void givenCurrentRaceACTIVE_ExpectStatusFOUND() {
        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        when(repository.findByRaceId(anyString())).thenReturn(currentRaceStatus);

        StartRaceService.ReturnStatus result = startRaceService.startRace(callbackUrl);

        assertNotNull(result);
        assertEquals(StartRaceService.ReturnStatus.FOUND, result);

        verify(repository, times(1)).findByRaceId(anyString());
        verify(timerService, times(1)).startTimer();
        verify(repository, never()).save(any(CurrentRaceStatus.class));
    }
}
