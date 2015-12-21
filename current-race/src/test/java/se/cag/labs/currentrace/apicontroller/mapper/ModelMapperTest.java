package se.cag.labs.currentrace.apicontroller.mapper;

import org.junit.Before;
import org.junit.Test;
import se.cag.labs.currentrace.apicontroller.apimodel.RaceStatus;
import se.cag.labs.currentrace.services.repository.datamodel.CurrentRaceStatus;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ModelMapperTest {
    private CurrentRaceStatus currentRaceStatus;

    @Before
    public void setup() {
        currentRaceStatus = new CurrentRaceStatus();
    }

    @Test
    public void GivenNull_ExpectStateINACTIVE() {
        RaceStatus result = ModelMapper.createStatusResponse(null);

        assertNotNull(result);
        assertEquals(RaceStatus.State.INACTIVE, result.getState());
        assertNull(result.getStartTime());
        assertNull(result.getMiddleTime());
        assertNull(result.getFinishTime());
        assertNull(result.getEvent());
    }

    @Test
    public void GivenOnlyEvent_ExpectEverythingElseNull() {
        currentRaceStatus.setEvent(RaceStatus.Event.DISQUALIFIED);

        RaceStatus result = ModelMapper.createStatusResponse(currentRaceStatus);

        assertNotNull(result);
        assertEquals(RaceStatus.Event.DISQUALIFIED, result.getEvent());
        assertNull(result.getStartTime());
        assertNull(result.getMiddleTime());
        assertNull(result.getFinishTime());
        assertNull(result.getState());
    }

    @Test
    public void GivenOnlyStartTime_ExpectEverythingElseNull() {
        currentRaceStatus.setStartTime(1L);

        RaceStatus result = ModelMapper.createStatusResponse(currentRaceStatus);

        assertNotNull(result);
        assertNull(result.getEvent());
        assertEquals(new Date(1L), result.getStartTime());
        assertNull(result.getMiddleTime());
        assertNull(result.getFinishTime());
        assertNull(result.getState());
    }

    @Test
    public void GivenOnlyMiddleTime_ExpectEverythingElseNull() {
        currentRaceStatus.setMiddleTime(1L);

        RaceStatus result = ModelMapper.createStatusResponse(currentRaceStatus);

        assertNotNull(result);
        assertNull(result.getEvent());
        assertNull(result.getStartTime());
        assertEquals(new Date(1L), result.getMiddleTime());
        assertNull(result.getFinishTime());
        assertNull(result.getState());
    }

    @Test
    public void GivenOnlyFinishTime_ExpectEverythingElseNull() {
        currentRaceStatus.setFinishTime(1L);

        RaceStatus result = ModelMapper.createStatusResponse(currentRaceStatus);

        assertNotNull(result);
        assertNull(result.getEvent());
        assertNull(result.getStartTime());
        assertNull(result.getMiddleTime());
        assertEquals(new Date(1L), result.getFinishTime());
        assertNull(result.getState());
    }

    @Test
    public void GivenOnlyState_ExpectEverythingElseNull() {
        currentRaceStatus.setState(RaceStatus.State.ACTIVE);

        RaceStatus result = ModelMapper.createStatusResponse(currentRaceStatus);

        assertNotNull(result);
        assertNull(result.getEvent());
        assertNull(result.getStartTime());
        assertNull(result.getMiddleTime());
        assertNull(result.getFinishTime());
        assertEquals(RaceStatus.State.ACTIVE, result.getState());
    }
}
