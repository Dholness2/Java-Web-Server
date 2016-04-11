package com.javawebserver.app.workers;

import com.javawebserver.app.workers.Worker;
import com.javawebserver.app.workers.ClientWorkerService;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Get;
import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.sockets.ClientSocket;
import com.javawebserver.app.Responder;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.Logger;
import com.javawebserver.app.StatusCodes;

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
  private static final String BREAK_LINE = "\\r?\\n";
  private ClientWorkerService testWorker;
  private Socket wrapper;
  private java.net.Socket mockSocket;
  private HashMap<String, ArrayList<String>> routes;
  private Responder testResponder;
  private LoggerMock loggerMock;

  @Before
  public  void responderSertup() {
    HashMap<String, Response> routes = new HashMap<String,Response>();
    routes.put("GET /", new Get("HTTP/1.1 200 ok"));
    testResponder =  new Responder(routes);
    loggerMock = new LoggerMock("/foobar");
  }

  @Test
  public void clonesClientWorkerTest() throws Exception {
    mockSocket = new ClientSocketMock("localHost", 9999, "GET /foo HTTP/1.1");
    wrapper = new ClientSocket(mockSocket);
    testWorker = new ClientWorkerService(testResponder, loggerMock);
    Worker workerClone = testWorker.clone();
    assertEquals(ClientWorkerService.class, workerClone.getClass());
  }

  @Test
  public void getAndSetsSocket() throws Exception {
    mockSocket = new ClientSocketMock("localHost",9999, "GET /foo HTTP/1.1");
    wrapper = new ClientSocket(mockSocket);
    testWorker = new ClientWorkerService(testResponder, loggerMock);
    testWorker.setSocket(wrapper);
    assertEquals(wrapper ,testWorker.getSocket());
  }

  @Test
  public void respondsToClientsRequest() throws Exception {
    mockSocket = new ClientSocketMock("localHost", 9999, "GET / HTTP/1.1");
    wrapper = new ClientSocket(mockSocket);
    testWorker = new ClientWorkerService(testResponder, loggerMock);
    testWorker.setSocket(wrapper);
    testWorker.run();
    String response = mockSocket.getOutputStream().toString().split(BREAK_LINE)[0];
    assertEquals("HTTP/1.1 200 ok", response);
  }

  @Test
  public void logsRequest() throws Exception {
    mockSocket = new ClientSocketMock("localHost",9999, "GET /foo HTTP/1.1");
    wrapper = new ClientSocket(mockSocket);
    testWorker = new ClientWorkerService(testResponder, loggerMock);
    testWorker.setSocket(wrapper);
    testWorker.run();
    assertEquals(true, loggerMock.requestLogged());
  }


  private class LoggerMock extends Logger {
    private boolean logged = false;
    private String mockPath;

    public LoggerMock(String path) {
      super(path);
    }

    public void logRequest(Request request) {
      this.logged = true;
    }

    public boolean requestLogged() {
      return logged;
    }
  }

  private class ClientSocketMock extends java.net.Socket {
    private String inputMessage = "GET / HTTP/1.1";
    private String serverName = null;
    private ByteArrayInputStream input;
    private ByteArrayOutputStream outPut = new ByteArrayOutputStream();
    private int port;

    public ClientSocketMock(String serverName, int port, String request) {
      this.serverName  = serverName;
      this.port = port;
      this.input = new ByteArrayInputStream(request.getBytes());
    }

    public OutputStream getOutputStream() {
      return this.outPut;
    }

    public InputStream getInputStream() {
      return this.input;
    }
  }
}
