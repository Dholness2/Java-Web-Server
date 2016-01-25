package com.JavaWebServer.app;
import java.io.*;
import java.net.*;

import org.junit.*;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.instanceOf;

public class SocketWraperTest {

  private  WrapperSocket testWrapperSocket;
  private  ServerSocket testServer;
  @Before
  public void  socketSetup() throws Exception {
    testServer = new ServerSocket(9997);
    Socket testSocket = new Socket("localhost",9997);
    WrapperSocket testWrapperSocket = new WrapperSocket(testSocket);
  }

  @After
  public void socketDeconstruct() throws Exception{
   testServer.close();
  }

  @Test
  public void TestWrapperisClosed() {
    assertFalse(testWrapperSocket.isClosed());
  }

  @Test
  public void TestgetOutputStream() {
    OutputStream testStream  =  testWrapperSocket.getOutputStream();
    assertTrue(testStream instanceof OutputStream);
  }

  @Test
  public void TestgetinputStream() {
    InputStream testStream = testWrapperSocket.getInputStream();
    assertTrue(testStream instanceof InputStream);
  }
}
