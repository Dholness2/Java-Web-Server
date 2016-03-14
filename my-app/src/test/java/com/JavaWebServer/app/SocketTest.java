package com.JavaWebServer.app;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class SocketTest {

  private final String BREAK_LINE = "\\r?\\n";
  private MockSocket mockSocket;
  private Socket wrapper;
  private StatusCodes codes;
  @Before
  public void buildSockets() {
    mockSocket = new MockSocket();
    wrapper = new Socket(mockSocket);
    codes = new StatusCodes();
  }

  @Test
  public void testGetRequest() {
    String route = "/foo";
    String requestMessage = "GET /foo HTTP/1.1";
    mockSocket.setRequest(requestMessage);
    Request request = wrapper.getRequest();
    assertEquals(request.getRoute(), route);
  }

  @Test
  public void testsendResponse() {
    byte [] response = (codes.OK).getBytes();
    wrapper.sendResponse(response);
    String output = mockSocket.getOutputStream().toString().split(BREAK_LINE)[0];
    assertEquals(codes.OK, output);
  }

 @Test
  public void testClose() {
    wrapper.close();
    assertTrue(mockSocket.closedStatus());
  }

  private class MockSocket extends java.net.Socket{
    private ByteArrayInputStream input;
    private ByteArrayOutputStream output;
    private boolean closed = false;

    public MockSocket() {
      this.output = new ByteArrayOutputStream();
    }

    public void setRequest(String request) {
      this.input = new ByteArrayInputStream(request.getBytes());
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

    public boolean closedStatus() {
      return this.closed;
    }
  }
}
