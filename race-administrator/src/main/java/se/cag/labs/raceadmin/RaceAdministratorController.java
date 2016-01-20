package se.cag.labs.raceadmin;

import io.swagger.annotations.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import se.cag.labs.raceadmin.services.*;
import se.cag.labs.usermanager.*;

import java.util.*;
import java.util.concurrent.*;

@Log4j
@RestController
@Api(basePath = "*",
        value = "Race Administrator",
        description = "This service administrates the races.",
        produces = "application/json")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RaceAdministratorController {
    @Autowired
    private UserQueueRepository userQueueRepository;
    @Autowired
    private ActiveRaceRepository activeRaceRepository;
    @Autowired
    private LeaderBoardService leaderBoardService;
    @Autowired
    private CurrentRaceService currentRaceService;

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/userqueue", method = RequestMethod.POST)
    @ApiOperation(value = "Registers a user as a competitor in a race.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User is registered for race.")
    })
    public void registerForRace(@RequestBody User user) {
        log.debug("POST /userqueue:" + user);
        Queue<User> sfsd = new ArrayBlockingQueue<>(10);
        sfsd.addAll(userQueueRepository.findAll());
        sfsd.add(user);
        userQueueRepository.save(sfsd);
        if (sfsd.size() == 1) {
            startNextRace();
        }
    }

        private void startNextRace() {
        Queue<User> sfsd = new ArrayBlockingQueue<>(10);
        sfsd.addAll(userQueueRepository.findAll());
        User user = sfsd.remove();
        userQueueRepository.save(sfsd);
        activeRaceRepository.save(new RaceStatus(user));
        currentRaceService.startRace();
    }

    @RequestMapping(value = "/userqueue", method = RequestMethod.GET)
    @ApiOperation(value = "Get the contents of the queue.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The queued users.")
    })
    public List<User> getQueue() {
        log.debug("GET /userqueue");
        return userQueueRepository.findAll();
    }

    @RequestMapping(value = "/onracestatusupdate", method = RequestMethod.POST)
    @ApiOperation(value = "Handle status updates for the current race.")
    public void onRaceStatusUpdate(@RequestBody RaceStatus status) {
        // TODO kontrollera att användaren finns och är aktiv
        // TODO ersätt med en builder?
        log.debug("onracestatusupdate:" + status);
        Optional<RaceStatus> activeRace = activeRaceRepository.findAll().stream().findFirst();
        if (activeRace.isPresent()) {
            if (status.getState() == RaceStatus.RaceState.INACTIVE) {
                UserResult userResult = new UserResult();
                userResult.setUser(activeRace.get().getUser());
                if (status.getEvent() == RaceStatus.RaceEvent.FINISH) {
                    userResult.setTime(status.getFinishTime() - status.getStartTime());
                    userResult.setMiddleTime(status.getMiddleTime() - status.getStartTime());
                    userResult.setResult(ResultType.FINISHED);
                } else if (status.getEvent() == RaceStatus.RaceEvent.TIME_OUT_NOT_STARTED) {
                    userResult.setResult(ResultType.WALKOVER);
                } else {
                    userResult.setResult(ResultType.DISQUALIFIED);
                }

                leaderBoardService.newResult(userResult);
                activeRaceRepository.delete(activeRace.get());
                startNextRace();
            } else {
                activeRace.get().setEvent(status.getEvent());
                activeRace.get().setState(status.getState());
                activeRace.get().setStartTime(status.getStartTime());
                activeRace.get().setMiddleTime(status.getMiddleTime());
                activeRace.get().setFinishTime(status.getFinishTime());
                activeRaceRepository.save(activeRace.get());
            }
        }
    }

    @RequestMapping(value = "/userqueueunregister", method = RequestMethod.POST)
    @ApiOperation(value = "Unegisters a user as a competitor in a race.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User is unregistered for race.")
    })
    public void unregisterForRace(@RequestBody User user) {
        log.debug("POST /userqueueunregister:" + user);
        Queue<User> sfsd = new ArrayBlockingQueue<>(10);
        sfsd.addAll(userQueueRepository.findAll());
        sfsd.remove(user);
        userQueueRepository.save(sfsd);
    }
}
