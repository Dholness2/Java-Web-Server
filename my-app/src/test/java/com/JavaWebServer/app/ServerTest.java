package com.JavaWebServer.app;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import java.net.*;
import java.io.*;

public class ServerTest  {
  Server testServer;
  
  @Before 
  public void setupServer () {
    int  port = 8787;
    testServer = new Server(port);
  }

  @Test
  public void TestisServerOnEqualsTrue () {
    assertTrue(testServer.isServerOn());
    }
  @Test
  public void TestisServerOnEqualsFalse () {
    testServer.off();
    assertFalse(testServer.isServerOn());
  }

  @Test
  public void TestServerTurnsoff () {
    testServer.off();
    assertFalse(testServer.isServerOn());
  }

 @Test 
  public void  TestServerGetsClientMessage () throws Exception {
    Thread testThread = new Thread(testServer);
    testThread.start();
    testThread.sleep(100); 
    
 
    Socket clientTestSocket = new Socket("localhost", 8787);
    System.out.println( "Client: connected to Server");
    PrintWriter outPut = new PrintWriter( clientTestSocket.getOutputStream(), true);
    outPut.println("GET / HTTP/1.1");
    System.out.println("Client: message sent from Client Get /HTTP/1.1");
    InputStreamReader stream = new InputStreamReader(clientTestSocket.getInputStream());
    BufferedReader response = new BufferedReader(stream);
    String message =  readResponse(response);
    assertEquals("HTTP/1.1 200 okHello world", message);
    System.out.println("Client: Message Recieved " + message);
  }

  private String readResponse (BufferedReader buffer) throws Exception {
    String message ="";
    String line;
    while ((line = buffer.readLine()) != null) {
      message += line;
    }
    return message;
  }
}
