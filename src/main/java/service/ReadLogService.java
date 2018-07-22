package service;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import type.Event;
import type.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadLogService {

  public List<Event> getEventList() {
    List<Log> logs = new ArrayList<Log>();
    for (String json : Arrays.asList(readLogs().split("\\r?\\n"))) {
      JSONObject obj = new JSONObject(json);
      logs.add(new Log(obj));
    }

    List<Event> events = new ArrayList<Event>();
    for (int i = 0; i < logs.size(); i++) {
      for (int j = 0; j < logs.size(); j++) {
        Log log1 = logs.get(i);
        Log log2 = logs.get(j);
        if (log1.getId().equals(log2.getId()) && !log1.getState().equals(log2.getState()) && !containsEvent(events, log1)) {
          events.add(new Event(log1, log2.getTimeStamp()));
        }
      }
    }
    return events;
  }

  private boolean containsEvent(List<Event> events, Log log1) {
    return events.stream().filter(event -> event.getId().equals(log1.getId())).findFirst().isPresent();
  }

  private String readLogs() {
    String data = "";
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("input.json").getFile());
    try {
      data = FileUtils.readFileToString(file, "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }
}
