package service;

import type.Event;

import java.sql.SQLException;

public class LoggingService {

  private ReadLogService readLogService = new ReadLogService();

  public LoggingService() {

    DatabaseService db = null;
    try {
      db = new DatabaseService();
    } catch (Exception ex1) {
      ex1.printStackTrace();    // could not start db
      return;
    }

    try {
      db.update("CREATE TABLE events ( id VARCHAR(256), type VARCHAR(256), duration INTEGER, host VARCHAR(256), alert BOOLEAN)");
    } catch (SQLException ex2) {
      ex2.printStackTrace();    // could not create table
      return;
    }

    try {
      for (Event event : readLogService.getEventList()) {
        String values = "'" + event.getId() + "','" + event.getType() + "'," + event.getDuration() + ",'" + event.getHost() + "'," + event.isAlert();
        db.update("INSERT INTO events(id,type,duration,host,alert) VALUES(" + values + ")");
      }

      // do a query
      db.query("SELECT * FROM events");

      // at end of program
      db.shutdown();
    } catch (SQLException ex3) {
      ex3.printStackTrace();
    }

  }

}
