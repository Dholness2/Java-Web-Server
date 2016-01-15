package com.JavaWebServer.app;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

import java.net.*;
import java.io.*;

public class ServerTest  {
  Server testServer;
  
  @Before 
  public void setupServer () {
    int  port = 9999;
    testServer = new Server(port);
  }

 @Test 
  public void ServerGetsCleintMessage () throws Exception {
    Thread testThread =  new Thread(testServer);
    testThread.start();
    testThread.sleep(100);
    System.out.println("pre connection");  
 
    Socket clientTestSocket = new Socket("localhost", 9999);
    System.out.println( "Client connected to Server");
    PrintWriter outPut = new PrintWriter( clientTestSocket.getOutputStream(), true);
    outPut.print("GET / HTTP/1.1");
    System.out.println("message sent from Client");
    InputStreamReader stream = new InputStreamReader(clientTestSocket.getInputStream());
    BufferedReader response = new BufferedReader (stream);
    String message  = response.readLine();
    clientTestSocket.close();
    assertEquals("200",message);
    }
  }
