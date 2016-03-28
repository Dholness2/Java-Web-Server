package com.JavaWebServer.app;

import com.JavaWebServer.app.serverSockets.ServerSocketWrapper;
import com.JavaWebServer.app.serverSockets.ServerSocket;

import java.io.IOException;

import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ServerSocketWrapperTest {
 private ServerSocket testServerSocket = new ServerSocketWrapper(9056);

 @After
  public void closeSockets() {
   testServerSocket.close();
  }

 @Test
  public void TestServerWrapperisclosed() throws Exception {
    assertFalse(testServerSocket.isClosed());
  }

  @Test
  public void TestServerclose() throws Exception {
    testServerSocket.close();
    assertTrue(testServerSocket.isClosed());
  }

  @Test
  public void TestServerSocketAccept() throws Exception {
    MockServerSocket mockServerSocket = new MockServerSocket();
    ServerSocket testWrapper =  new ServerSocketWrapper(mockServerSocket);
    testWrapper.accept();
    assertTrue(mockServerSocket.hasCalledAccept());
  }

  class MockServerSocket extends java.net.ServerSocket  {

   private boolean acceptWasCalled = false;

   public MockServerSocket () throws Exception {
   }

   public java.net.Socket accept()  {
     acceptWasCalled = true;
     return null;
   }

   public boolean hasCalledAccept() {
      return acceptWasCalled;
   }
  }
}
