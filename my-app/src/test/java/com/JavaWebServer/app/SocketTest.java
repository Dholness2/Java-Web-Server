package com.JavaWebServer.app;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import org.junit.*;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.instanceOf;

public class SocketTest {

  private  com.JavaWebServer.app.Socket testSocket;
  private  ServerSocket testServer;

  @Before
  public void  socketSetup() throws Exception {
    testServer = new ServerSocket(9997);
    java.net.Socket apiSocket = new Socket("localhost",9997);
    testSocket = new com.JavaWebServer.app.Socket(apiSocket);
  }

  @After
  public void socketDeconstruct() throws Exception{
  testServer.close();
  testSocket.close();
  }

  @Test
  public void TestisClosed()throws Exception {
    assertFalse(testSocket.isClosed());
  }

  @Test
  public void TestgetOutputStream() throws Exception{
    OutputStream testStream  =  testSocket.getOutputStream();
    assertTrue(testStream instanceof OutputStream);
  }

  @Test
  public void TestgetinputStream() throws Exception{
    InputStream testStream = testSocket.getInputStream();
    assertTrue(testStream instanceof InputStream);
  }
}
