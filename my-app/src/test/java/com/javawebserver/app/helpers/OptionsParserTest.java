package com.javawebserver.app;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.InputStream;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class OptionsParserTest  {
  private final ByteArrayOutputStream outPut = new ByteArrayOutputStream();

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outPut));
  }

  @After
  public void cleanUpStreams() {
    outPut.reset();
  }

  @Test
  public void invalidPortNumberTest() {
    String[] testArgs = {"-p","500I"};
    String inputData = "5000";
    System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
    Scanner testScanner =  new Scanner(System.in);
    OptionsParser testParser = new OptionsParser(testScanner, testArgs);
    int port = testParser.getPort();
    assertEquals(5000, port);
  }

  @Test
  public void respondsToInvalidPortTest() {
    String[] testArgs = {"-p","500I"};
    String inputData = "5000";
    System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
    Scanner testScanner =  new Scanner(System.in);
    OptionsParser testParser = new OptionsParser(testScanner, testArgs);
    testParser.getPort();
    String expectedOutPut = "Invalid port number please enter new port:";
    assertEquals(expectedOutPut, outPut.toString());
  }

  @Test
  public void getsValidPortNumberTest() {
    String[] testArgs = {"-p","5000"};
    Scanner testScanner =  new Scanner(System.in);
    OptionsParser testParser = new OptionsParser(testScanner, testArgs);
    int port = testParser.getPort();
    assertEquals(5000, port);
  }

  @Test
  public void checksInvalidDirectoriesTest() {
    String[] testArgs = {"-d","helloWorld"};
    String inputData = "/";
    System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
    Scanner testScanner =  new Scanner(System.in);
    OptionsParser testParser = new OptionsParser(testScanner, testArgs);
    String directory = testParser.getDirectory();
    assertEquals("/", directory);
  }

  @Test
  public void respondsToInvalidDirectoryTest() {
    String[] testArgs = {"-d","helloWorld"};
    String inputData = "/";
    System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
    Scanner testScanner = new Scanner(System.in);
    OptionsParser testParser = new OptionsParser(testScanner, testArgs);
    testParser.getDirectory();
    String expectedOutPut = "Path does not exist, enter correct path:";
    assertEquals(expectedOutPut, outPut.toString());
  }

  @Test
  public void getsValidDirectoriesTest() {
    String[] testArgs = {"-d", "/"};
    Scanner testScanner =  new Scanner(System.in);
    OptionsParser testParser = new OptionsParser(testScanner, testArgs);
    String directory = testParser.getDirectory();
    assertEquals("/", directory);
  }
}
