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
    int  port = 2000;
    testServer = new Server(port);
  }
 
  @Test 
  public void ServerGetsCleintMessage () throws Exception {
  Thread testS =  new Thread(testServer);
   testS.run();
 
   PrintWriter outPut;
   Socket clientTestSocket = new Socket("localhost", 2000);
   outPut = new PrintWriter( clientTestSocket.getOutputStream(), true);
   outPut.print("GET / HTTP/1.1");
   System.out.println("message sent from Client");
   InputStreamReader stream = new InputStreamReader(clientTestSocket.getInputStream());
   BufferedReader response = new BufferedReader (stream);
   String message  = response.readLine();
   clientTestSocket.close();
   assertEquals("200",message); 
   }
  }
