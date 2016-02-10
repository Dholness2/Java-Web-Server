package com.JavaWebServer.app;

import java.net.Socket;
import java.io.*;
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


  class MockServerSocket extends java.net.ServerSocket  {

   private boolean acceptWasCalled = false;

   public MockServerSocket () throws Exception {
   }

   public java.net.Socket accept()  {
     acceptWasCalled = true;
     return null;
   }

   public boolean acceptWasCalled() {
      return acceptWasCalled;
   }
  }

  @Test
  public void TestServerSocketAccept() throws Exception {
    MockServerSocket mockServerSocket = new MockServerSocket();
    ServerSocket testWrapper =  new ServerSocket(mockServerSocket);

    testWrapper.accept();

    assertTrue(mockServerSocket.acceptWasCalled());
  }
}
