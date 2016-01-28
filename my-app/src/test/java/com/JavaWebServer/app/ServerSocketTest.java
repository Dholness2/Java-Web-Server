package com.JavaWebServer.app;

import java.net.Socket;

import static org.junit.Assert.*;
import org.junit.*;

public class ServerSocketTest {

  @Test
  public void TestServerWrapperisclosed() throws Exception {
    ServerSocket testServer = new ServerSocket(9056 );
    assertFalse(testServer.isClosed());
     testServer.close();
  }

  @Test
  public void TestServerclose() throws Exception {
    ServerSocket testWrapper = new ServerSocket(9056);
    testWrapper.close();
    assertTrue(testWrapper.isClosed());
  }

  @Test
  public void TestServerSocketAccept() throws Exception {
    ServerSocket testWrapper =  new ServerSocket(9056);
    Socket client = new Socket("localhost",9056);
    com.JavaWebServer.app.Socket clientSocketWrapper = testWrapper.accept();
    assertTrue(clientSocketWrapper instanceof  com.JavaWebServer.app.Socket);
    testWrapper.close();
    clientSocketWrapper.close();
  }
}
