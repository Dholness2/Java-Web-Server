package com.JavaWebServer.app;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

import java.net.*;
import java.io.*;

public class ServerTest  {
  @Before 
  public void setupServer () {
    int  port = 9090;
    Server TestServer = new Server(port);
    TestServer.run; 
   }
 
  @Test 
  public void ServerGetsCleintMessage () {
   Socket clientTestSocket = new Socket("Local Host", 9090);
   PrintWriter outputs = new PrintWriter(clientTestSocket.getOutputStream(), true);
   BufferedReader inputs =new BufferedReader( new InputStreamReader(clientTestSocket.getInputStream()));
   outputs.print("GET / HTTP/1.1");  
 }
}
