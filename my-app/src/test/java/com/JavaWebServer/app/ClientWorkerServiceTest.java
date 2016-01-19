package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import java.net.*;
import java.io.*;


public class ClientWorkerServiceTest {
  
  @Test
  public void respondsToClientsRequest() throws Exception {
    Socket clientTestSocket = new ClientSocketMock("localHost", 9999);
    new ClientWorkerService(clientTestSocket, "local host").run();
    PrintWriter outPut = new PrintWriter(clientTestSocket.getOutputStream(),true);
    outPut.print("GET / http/1.1");
    BufferedReader response = new BufferedReader(new InputStreamReader(clientTestSocket.getInputStream())); 
    assertEquals("200", response.readLine());
  }
  
  private class ClientSocketMock extends Socket {
     String inputMessage = "200";
     String  serverName = null;
     int port;
    public ClientSocketMock (String serverName, int port) {
      this.serverName  = serverName;
      this.port = port;
    } 
     public OutputStream getOutputStream() {
       return  new ByteArrayOutputStream();
     }

     public InputStream getInputStream () {
       return new  ByteArrayInputStream(inputMessage.getBytes());
     }
   }
 }
