package com.javawebserver.app.helpers;

import com.javawebserver.app.Request;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.junit.Test;
import org.junit.Before;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;

public class LoggerTest {
  private static String logPath = System.getProperty("user.dir")+"/logs";
  private Logger testlogger = new Logger(logPath);
  private Request testRequest = new Request();

  private boolean containsEdit(String edit) throws IOException {
    File logs = new File(logPath);
    Scanner testScan = new Scanner(logs);
    int lineNum = 0;
    while (testScan.hasNextLine()) {
      String line = testScan.nextLine();
      lineNum++;
      if(line.contains(edit)) {
        return true;
      }
    }
    return false;
  }

  @AfterClass
  public static void removeTestData() {
    File testLogs = new File(logPath);
    testLogs.delete();
  }

  @Test
  public void logRequestTest() throws IOException {
    String routeRequest = "GET /foobar HTTP/1.1";
    testRequest.setMessage(routeRequest);
    testlogger.logRequest(testRequest);
    assertEquals(true,containsEdit(routeRequest));
  }

  @Test
  public void clearLogsTest() throws IOException {
    testlogger.clearLogs();
    assertEquals(false, containsEdit("GET /foobar HTTP/1.1"));
  }
}
