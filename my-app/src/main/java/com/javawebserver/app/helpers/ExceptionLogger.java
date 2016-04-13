package com.javawebserver.app.helpers;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class ExceptionLogger {
  private static String logPath = System.getProperty("user.dir") + "/ExceptionsLogs";

  public static void logException(String exception) {
    try {
      FileWriter writer = new FileWriter(logPath,true);
      BufferedWriter logger = new BufferedWriter(writer);
      logger.append(exception);
      logger.newLine();
      logger.close();
    } catch (IOException e) {
      System.out.println("logger can not log Exception: " + e);
    }
  }
}
