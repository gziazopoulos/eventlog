package type;

import lombok.Data;
import org.json.JSONObject;

@Data
public class Log {
  String id;
  String state;
  Long timeStamp;
  String host;
  String type;

  public Log(JSONObject obj) {
    id = obj.getString("id");
    state = obj.getString("state");
    host = obj.has("host") ? obj.getString("host") : "";
    type = obj.has("type") ? obj.getString("type") : "";
    timeStamp = obj.getLong("timestamp");

  }
}
