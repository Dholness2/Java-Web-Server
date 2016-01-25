package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;

import org.hamcrest.core.IsInstanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.net.*;
import java.io.*;

public class ClientWorkerServiceTest {
  private ClientWorkerService testWorker;
  private WrapperSocket wrapper;
  private Socket clientTestSocket;

  @Before
  public void mocksetup() {
    clientTestSocket = new ClientSocketMock("localHost", 9999);
    wrapper = new WrapperSocket(clientTestSocket);
    testWorker = new ClientWorkerService(wrapper, "local host");
  }

  @Test
  public void respondsToClientsRequest() throws Exception {
    testWorker.run();
    PrintWriter outPut = new PrintWriter(clientTestSocket.getOutputStream(),true);
    outPut.print("GET / http/1.1");
    BufferedReader response = new BufferedReader(testWorker.getStreamReader()); 
    assertEquals("200", response.readLine());
  }

  @Test
  public void testgetStreamReader() throws Exception {
    assertTrue(testWorker.getStreamReader() instanceof InputStreamReader);
  }

  @Test
  public void testgetOutputStream() throws Exception {
    assertTrue(testWorker.getOutputStream() instanceof OutputStream);
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
