package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

public class ClientWorkerServiceTest {
  private final String BREAK_LINE = "\\r?\\n";

  private ClientWorkerService testWorker;
  private Socket wrapper;
  private java.net.Socket mockSocket;

  @Test
  public void respondsToClientsRequest() throws Exception {
    mockSocket = new ClientSocketMock("localHost", 9999, "GET / http/1.1");
    wrapper = new Socket(mockSocket);
    testWorker = new ClientWorkerService(wrapper, "local host");
    testWorker.run();
    String response = mockSocket.getOutputStream().toString().split(BREAK_LINE)[0];
    assertEquals("HTTP/1.1 200 ok",response);
  }

  @Test
  public void respondsWith404() throws Exception {
    mockSocket = new ClientSocketMock("localHost",9999, "GET /foo http/1.1");
    wrapper = new Socket(mockSocket);
    testWorker = new ClientWorkerService(wrapper, "local host");
    testWorker.run();
    String response = mockSocket.getOutputStream().toString().split(BREAK_LINE)[0];
    assertEquals("HTTP/1.1 404 not found",response);
  }

  private class ClientSocketMock extends java.net.Socket {
    private String inputMessage = "GET / http/1.1";
    private  String  serverName = null;
    private ByteArrayInputStream input;
    private ByteArrayOutputStream outPut = new ByteArrayOutputStream();
    private int port;

    public ClientSocketMock (String serverName, int port, String request) {
      this.serverName  = serverName;
      this.port = port;
      this.input = new ByteArrayInputStream(request.getBytes());
    } 
    public OutputStream getOutputStream() {
      return  this.outPut;
    }

    public InputStream getInputStream () {
      return this.input;
    }
  }
}
