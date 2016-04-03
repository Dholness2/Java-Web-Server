package com.javawebserver.app;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Logger {
  private String logPath;
  private static final String  PROTOCOL = " HTTP/1.1";

  public Logger (String logPath) {
    this.logPath = logPath;
  }

  public void logRequest(Request request){
    try{
      FileWriter writer = new FileWriter(logPath,true);
      BufferedWriter logger = new BufferedWriter(writer);
      logger.append(request.getRequest() + PROTOCOL);
      logger.newLine();
      logger.close();
    } catch (IOException e) {
      new Exception("Path not found:").printStackTrace();
      e.printStackTrace();
    }
  }

  public void clearLogs() {
   try{
      FileWriter writer = new FileWriter(logPath);
      BufferedWriter logger = new BufferedWriter(writer);
      logger.write("");
      logger.close();
    } catch (Exception e) {
      new Exception("Path not found:").printStackTrace();
      e.printStackTrace();
    }
  }
}
