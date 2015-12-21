package se.cag.labs.currentrace.timer;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.CallbackService;
import se.cag.labs.currentrace.services.repository.CurrentRaceRepository;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class VerifyRacePassagesTimerTaskTest {
    @InjectMocks
    private VerifyRacePassagesTimerTask verifyRacePassagesTimerTask;
    @Mock
    private CurrentRaceRepository repository;
    @Mock
    private CallbackService callbackService;
    private CurrentRaceStatus currentRaceStatus;
    private ArgumentCaptor<CurrentRaceStatus> argument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        currentRaceStatus = new CurrentRaceStatus();
        argument = ArgumentCaptor.forClass(CurrentRaceStatus.class);
    }

    @Test
    public void givenStatusINACTIVEAndConsistent_ExpectNoRepositoryOrCallback() {
        currentRaceStatus.setState(RaceStatus.State.INACTIVE);

        when(repository.findByRaceId(anyString())).thenReturn(currentRaceStatus);

        verifyRacePassagesTimerTask.run();

        verify(repository, never()).save(any(CurrentRaceStatus.class));
        verify(callbackService, never()).reportStatus(any(CurrentRaceStatus.class));
    }

    @Test
    public void givenStartTimeNullAndActivatedOverThreeMinutesExpectTIMEOUT_NOT_STARTED() {
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        currentRaceStatus.setStartTime(null);
        currentRaceStatus.setRaceActivatedTime(new DateTime().minusMinutes(3).minusSeconds(1).getMillis());

        when(repository.findByRaceId(anyString())).thenReturn(currentRaceStatus);

        verifyRacePassagesTimerTask.run();

        verify(repository, times(1)).save(argument.capture());
        assertEquals(RaceStatus.Event.TIME_OUT_NOT_STARTED, argument.getValue().getEvent());
        assertEquals(RaceStatus.State.INACTIVE, argument.getValue().getState());
        verify(callbackService, times(1)).reportStatus(argument.capture());
        assertEquals(RaceStatus.Event.TIME_OUT_NOT_STARTED, argument.getValue().getEvent());
        assertEquals(RaceStatus.State.INACTIVE, argument.getValue().getState());

    }

    @Test
    public void givenMiddleTimeNullAndActivatedOverThreeMinutesExpectDISQUALIFIED() {
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        currentRaceStatus.setStartTime(new DateTime().minusMinutes(3).minusSeconds(1).getMillis());
        currentRaceStatus.setMiddleTime(null);
        currentRaceStatus.setRaceActivatedTime(new DateTime().getMillis());

        when(repository.findByRaceId(anyString())).thenReturn(currentRaceStatus);

        verifyRacePassagesTimerTask.run();

        verify(repository, times(1)).save(argument.capture());
        assertEquals(RaceStatus.Event.DISQUALIFIED, argument.getValue().getEvent());
        assertEquals(RaceStatus.State.INACTIVE, argument.getValue().getState());
        verify(callbackService, times(1)).reportStatus(argument.capture());
        assertEquals(RaceStatus.Event.DISQUALIFIED, argument.getValue().getEvent());
        assertEquals(RaceStatus.State.INACTIVE, argument.getValue().getState());
    }

    @Test
    public void givenFinishTimeNullAndActivatedOverThreeMinutesExpectNOT_FINISHED() {
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);
        currentRaceStatus.setStartTime(new DateTime().getMillis());
        currentRaceStatus.setMiddleTime(new DateTime().minusMinutes(3).minusSeconds(1).getMillis());
        currentRaceStatus.setFinishTime(null);
        currentRaceStatus.setRaceActivatedTime(new DateTime().getMillis());

        when(repository.findByRaceId(anyString())).thenReturn(currentRaceStatus);

        verifyRacePassagesTimerTask.run();

        verify(repository, times(1)).save(argument.capture());
        assertEquals(RaceStatus.Event.TIME_OUT_NOT_FINISHED, argument.getValue().getEvent());
        verify(callbackService, times(1)).reportStatus(argument.capture());
        assertEquals(RaceStatus.Event.TIME_OUT_NOT_FINISHED, argument.getValue().getEvent());
    }
}
