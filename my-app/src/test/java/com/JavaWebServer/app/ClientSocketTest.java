package com.JavaWebServer.app;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import org.junit.*;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


public class ClientSocketTest { 

  @Test
  public void testGetRequest() {
    String route = "/foo";
    String requestMessage = "GET /foo http 1.1";
    Socket testSocket = new MockClientSocket(requestMessage);
    ClientSocket wrapper =  new ClientSocket(testSocket);
    Request request =  wrapper.getRequest();
    assertEquals(request.getRoute(), route);
  }

  private class MockClientSocket extends java.net.Socket{
    private ByteArrayInputStream input;
    private ByteArrayOutputStream output;

    public MockClientSocket(String request) {
      this.input = new ByteArrayInputStream(request.getBytes());
    }

    public InputStream getInputStream() {
     return this.input;
    }

    public  OutputStream getOutputStream() {
     return this.output;
    }

  }
}
