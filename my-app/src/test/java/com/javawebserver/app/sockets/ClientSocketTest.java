package com.javawebserver.app.sockets;

import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.sockets.ClientSocket;
import com.javawebserver.app.StatusCodes;
import com.javawebserver.app.Request;

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

public class ClientSocketTest {
  private final String CRLF = System.getProperty("line.separator");
  private MockSocket mockSocket;
  private ClientSocket testSocket;
  private StatusCodes codes;

  @Before
  public void buildSockets() {
    mockSocket = new MockSocket();
    testSocket = new ClientSocket(mockSocket);
    codes = new StatusCodes();
  }

  @Test
  public void testGetRequestIntialHeaderTest() {
    String route = "/foo";
    String requestMessage = "GET /foo HTTP/1.1";
    mockSocket.setRequest(requestMessage);
    Request request = testSocket.getRequest();
    assertEquals(request.getRoute(), route);
  }

  @Test
  public void testGetRequestSecondaryHeaderTest() {
    String initalHeader = "POST /simplePost HTTP/1.1"+ CRLF;
    String additionalHeaders = "Host: test"+CRLF
                     +"Accept-Language: en-us"+ CRLF
                     +"Connection: Keep-Alive"+ CRLF
                     +"Content-type: text/html"+ CRLF
                     +"Content-Length:0 "+ CRLF;
    String fullRequest = initalHeader + additionalHeaders;
    mockSocket.setRequest(fullRequest);
    Request request = testSocket.getRequest();
    assertEquals(request.getHeaders(), additionalHeaders);
  }

  @Test
  public void testGetRequestBodyTest() {
    String initalHeader = "POST /simplePost HTTP/1.1"+ CRLF;
    String additionalHeaders = "Host: test"+CRLF
                     +"Accept-Language: en-us"+ CRLF
                     +"Connection: Keep-Alive"+ CRLF
                     +"Content-type: text/plain"+ CRLF
                     +"Content-Length: 11"+ CRLF+CRLF;
    String body = "Hello World";
    String fullRequest = initalHeader + additionalHeaders + body;
    mockSocket.setRequest(fullRequest);
    Request request = testSocket.getRequest();
    assertEquals(request.getBody(), body);
  }

  @Test
  public void testsendResponse() {
    byte [] response = (codes.OK).getBytes();
    testSocket.sendResponse(response);
    String output = mockSocket.getOutputStream().toString().split(CRLF)[0];
    assertEquals(codes.OK, output);
  }

 @Test
  public void testClose() {
    testSocket.close();
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
