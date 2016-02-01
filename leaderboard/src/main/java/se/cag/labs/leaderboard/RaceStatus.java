package se.cag.labs.leaderboard;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.*;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaceStatus {
  @Id
  @Setter(AccessLevel.PRIVATE)
  private String id;
  private User user;
  private RaceEvent event;
  private RaceState state;
  private Long startTime;
  private Long splitTime;
  private Long finishTime;
  private Long currentTime;
  public RaceStatus(User user) {
    this.user = user;
  }
  public enum RaceState {
    ACTIVE, INACTIVE
  }

  public enum RaceEvent {
    NONE, START, SPLIT, FINISH, TIME_OUT_NOT_STARTED, TIME_OUT_NOT_FINISHED, DISQUALIFIED
  }
}
