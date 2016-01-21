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
    ServerSocketWraper testWrapper =  new ServerSocketWraper(testServer);
    testWrapper.close();
    assertTrue(testWrapper.isClosed());
  }
  
  @Test 
  public void TestServerWrapperAccept() throws Exception {
    ServerSocket testServer = new ServerSocket(9056);
    ServerSocketWraper testWrapper =  new ServerSocketWraper(testServer);
    Socket client = new Socket("localhost",9056);
    WraperSocket clientSocket = testWrapper.accept();
    assertTrue(clientSocket instanceof WraperSocket);
    testWrapper.close();
    clientSocket.close(); 
  }
} 
