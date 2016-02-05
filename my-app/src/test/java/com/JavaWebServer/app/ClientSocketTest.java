package com.JavaWebServer.app;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import java.net.Socket;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


public class ClientSocketTest { 
  private final String BREAK_LINE = "\\r?\\n";

  @Test
    public void testGetRequest() {
      String route = "/foo";
      String requestMessage = "GET /foo http 1.1";
      Socket mockSocket = new MockSocket(requestMessage);
      ClientSocket wrapper = new ClientSocket(mockSocket);
      Request request =  wrapper.getRequest();
      assertEquals(request.getRoute(), route);
    }

  @Test 
    public void testsendResponse() {
      MockSocket mockSocket = new MockSocket();
      ClientSocket wrapper  = new ClientSocket(mockSocket);
      String response = "HTTP/1.1 200 ok";
      wrapper.sendResponse(response);
      String output = mockSocket.getOutputStream().toString().split(BREAK_LINE)[0];
      System.out.println(output);
      assertEquals(response, output);
    }

   @Test
   public void testClose() {
     MockSocket mockSocket = new MockSocket();
     ClientSocket wrapper  = new ClientSocket(mockSocket);
     wrapper.close();
     assertTrue(mockSocket.closedStatus());
   }

  private class MockSocket extends java.net.Socket{
    private ByteArrayInputStream input;
    private ByteArrayOutputStream output;
    private Boolean closed = false;

    public MockSocket(String request) {
      this.input = new ByteArrayInputStream(request.getBytes());
    }

    public MockSocket() {
      this.output = new ByteArrayOutputStream();
    }

    public InputStream getInputStream() {
      return this.input;
    }

    public OutputStream getOutputStream() { 
      return this.output;
    }

    public void close() {
      this.closed = true;
    }

    public Boolean closedStatus() {
      return this.closed;
    }
}
}
