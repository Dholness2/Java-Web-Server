package com.JavaWebServer.app;
import java.net.*;
import static org.junit.Assert.*;
import org.junit.*;

public class ServerSocketWraperTest {

  @Test
  public void TestServerWrapperisclosed() throws Exception {
    ServerSocket testServer = new ServerSocket(9056 );
    assertFalse(testServer.isClosed());
    testServer.close();
  }

  @Test
  public void TestServerWrapperclose() throws Exception {
    ServerSocket testServer = new ServerSocket(9056);
    ServerSocketWrapper testWrapper =  new ServerSocketWrapper(testServer);
    testWrapper.close();
    assertTrue(testWrapper.isClosed());
  }

  @Test
  public void TestServerWrapperAccept() throws Exception {
    ServerSocket testServer = new ServerSocket(9056);
    ServerSocketWrapper testWrapper =  new ServerSocketWrapper(testServer);
    Socket client = new Socket("localhost",9056);
    WrapperSocket clientSocket = testWrapper.accept();
    assertTrue(clientSocket instanceof WrapperSocket);
    testWrapper.close();
    clientSocket.close();
  }
}
