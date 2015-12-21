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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class StatusServiceTest {
    @InjectMocks
    private StatusService statusService;
    @Mock
    private CurrentRaceRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenRepositoryResult_ExpectResult() {
        CurrentRaceStatus currentRaceStatus = new CurrentRaceStatus();
        currentRaceStatus.setEvent(RaceStatus.Event.DISQUALIFIED);

        when(repository.findByRaceId(anyString())).thenReturn(currentRaceStatus);

        CurrentRaceStatus result = statusService.status();

        assertNotNull(result);
        assertEquals(RaceStatus.Event.DISQUALIFIED, result.getEvent());
    }
}
