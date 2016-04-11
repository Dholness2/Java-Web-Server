package com.javawebserver.app.helpers;

import com.javawebserver.app.helpers.ExceptionLogger;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.junit.Test;
import org.junit.Before;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;

public class ExceptionLoggerTest {
  private static String logPath = System.getProperty("user.dir") + "/ExceptionsLogs";

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
 public void logExceptions() throws IOException {
   String exception = "unable to read file";
   ExceptionLogger.logException(exception);
   assertTrue(containsEdit(exception));
 }
} 
