package com.javawebserver.app;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.sockets.ClientSocket;
import com.javawebserver.app.workers.Worker;
import com.javawebserver.app.workers.ClientWorkerService;
import com.javawebserver.app.serverSockets.ServerSocket;
import com.javawebserver.app.serverSockets.ServerSocketWrapper;
import com.javawebserver.app.helpers.Logger;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.net.Socket;

import java.io.File;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ServerTest  {
  private Server testServer;
  private ServerSocketMock serverSocketMock;
  private SocketMock socketMock = new SocketMock(new Socket());
  private String logPath = System.getProperty("user.dir") + "/logs";
  private int port = 9092;
  private Logger testLogger;

  @Before
  public void setupServer () throws Exception {
    HashMap<String, Response> routes = new HashMap<String,Response>();
    testLogger = new Logger(logPath);
    Responder testResponder =  new Responder(routes,new HttpResponseBuilder());
    Worker testWorker = new ClientWorkerService(testResponder, testLogger);
    serverSocketMock =  new ServerSocketMock(port);
    testServer = new Server(testWorker, serverSocketMock);
  }

  @After
  public void turnOffServer() {
    testLogger.clearLogs();
    testServer.off();
    File testForm = new File(logPath);
    testForm.delete();
  }

  @Test
  public void testisServerOnEqualsTrue() {
    assertTrue(testServer.isServerOn());
  }

  @Test
  public void testisServerOnEqualsFalse() {
    testServer.off();
    assertFalse(testServer.isServerOn());
  }

  @Test
  public void  testServerRunMethod() throws Exception {
    serverSocketMock.setSocket(socketMock);
    testServer.run();
    assertTrue(socketMock.workerWasAssigned());
    testServer.off();
  }

  private class SocketMock extends ClientSocket {
    private  boolean workerRan = false;

    public SocketMock(java.net.Socket socket) {
      super(socket);
    }

    public Request getRequest() {
      this.workerRan = true;
      testServer.off();
      Request fakeRequest = new Request();
      fakeRequest.setMessage("GET /foo HTTP/1.1");
      return fakeRequest;
    }

    public boolean workerWasAssigned() {
      return this.workerRan;
    }

    public void sendResponse(byte[] response) {}

    public void close() {}
  }

  private class ServerSocketMock implements ServerSocket{
    private SocketMock socketMock;

    public ServerSocketMock(int port) {}

    public void setSocket(SocketMock socket) {
      this.socketMock = socket;
    }

    public com.javawebserver.app.sockets.Socket accept() {
      return this.socketMock;
    }

    public void close() {}

    public boolean isClosed() {
      return true;
    }
  }
}
