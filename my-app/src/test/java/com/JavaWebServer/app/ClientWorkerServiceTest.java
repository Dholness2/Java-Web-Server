package com.JavaWebServer.app;

import java.util.ArrayList;
import java.util.HashMap;

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
  private HashMap<String, ArrayList<String>> routes;
  private Responder testResponder;

  @Before
   public  void responderSertup() {
    HashMap<String, RestMethod> methods = new HashMap<String,RestMethod>();
    methods.put("GET /", new Get("HTTP/1.1 200 ok"));
     ArrayList<String> routeMethods = new ArrayList<String>(); 
     routeMethods.add("GET");
     routes = new HashMap<String, ArrayList<String>>();
     routes.put("/",routeMethods);
     testResponder =  new Responder(routes, methods);
   }

  @Test
  public void respondsToClientsRequest() throws Exception {
    mockSocket = new ClientSocketMock("localHost", 9999, "GET / HTTP/1.1");
    wrapper = new Socket(mockSocket);
    testWorker = new ClientWorkerService(wrapper, testResponder);
    testWorker.run();
    String response = mockSocket.getOutputStream().toString().split(BREAK_LINE)[0];
    assertEquals("HTTP/1.1 200 ok",response);
  }

  @Test
  public void respondsWith404() throws Exception {
    mockSocket = new ClientSocketMock("localHost",9999, "GET /foo HTTP/1.1");
    wrapper = new Socket(mockSocket);
    testWorker = new ClientWorkerService(wrapper, testResponder);
    testWorker.run();
    String response = mockSocket.getOutputStream().toString().split(BREAK_LINE)[0];
    assertEquals("HTTP/1.1 404 not found",response);
  }

  private class ClientSocketMock extends java.net.Socket {
    private String inputMessage = "GET / HTTP/1.1";
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
