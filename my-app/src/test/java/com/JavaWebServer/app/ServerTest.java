package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ServerTest  {
 private Server testServer;
 private ServerSocketMock serverSocketMock;
 private SocketMock socketMock = new SocketMock(new Socket());
 private String logPath = System.getProperty("user.dir")+"/logs";
 private Logger testLogger;
  
  @Before
  public void setupServer () throws Exception {
    HashMap<String, RestMethod> methods = new HashMap<String,RestMethod>();
    HashMap<String,ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
    testLogger = new Logger(logPath);
    Responder testResponder =  new Responder(routes, methods);
    int  port = 9094;
    serverSocketMock =  new ServerSocketMock(port);
    testServer = new Server(port,serverSocketMock,testResponder, testLogger);
  }

  @After
  public void turnOffServer() {
    testLogger.clearLogs();
    testServer.off();
  }

  @Test
  public void testisServerOnEqualsTrue () {
    assertTrue(testServer.isServerOn());
  }
  
  @Test
  public void testisServerOnEqualsFalse () {
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
  
  private class SocketMock extends com.JavaWebServer.app.Socket {
  private  boolean workerRan = false; 
	   
    public SocketMock (java.net.Socket socket) {
     super(socket);
    }
  
    public Request getRequest() {
      this.workerRan = true;
      testServer.off();
      Request fakeRequest = new Request();
      fakeRequest.setMessage("GET /foo HTTP/1.1");
      return fakeRequest;
    }   

    public boolean workerWasAssigned () {
      return this.workerRan;
    }
  
    public void sendResponse(byte [] response) { }
  
    public void close() {}
 }

   private class ServerSocketMock implements InterfaceServerSocket{
   private SocketMock socketMock;
  
   public ServerSocketMock (int port) { }
  
   public void setSocket(SocketMock socket) {
     this.socketMock = socket;
   } 
  
   public com.JavaWebServer.app.Socket accept () {
     return this.socketMock;
   }
  
   public void close() {}

 }
}
