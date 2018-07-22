package type;

import lombok.Data;

@Data
public class Event {
  String id;
  String state;
  int duration;
  String host;
  String type;
  boolean alert;

  public Event(Log log, Long timeStamp) {
    id = log.getId();
    state = log.getState();
    host = log.getHost();
    type = log.getType();
    duration = Math.toIntExact(log.getTimeStamp() > timeStamp ? log.getTimeStamp() - timeStamp : timeStamp - log.getTimeStamp());
    alert = duration > 4 ? true : false;
  }
}
