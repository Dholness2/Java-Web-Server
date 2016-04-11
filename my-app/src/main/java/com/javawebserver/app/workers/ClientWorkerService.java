package com.javawebserver.app.workers;

import com.javawebserver.app.workers.Worker;
import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.helpers.Logger;
import com.javawebserver.app.Responder;
import com.javawebserver.app.Request;

import java.util.ArrayList;

public class ClientWorkerService implements Worker {
  private Socket client;
  private Responder responder;
  private Logger logger;
  private Request request;

  public Worker clone() {
    return new ClientWorkerService(this.responder, this.logger);
  }

  public ClientWorkerService (Responder responder, Logger logger) {
    this.responder = responder;
    this.logger = logger;
  }

  public void setSocket(Socket socket) {
    this.client = socket;
  }

  public Socket getSocket() {
    return this.client;
  }

  public void run() {
    this.request = client.getRequest();
    logRequest();
    client.sendResponse(getResponse());
    client.close();
    System.out.println ("Server: request Closed");
  }

  private byte[] getResponse() {
    return this.responder.getResponse(this.request);
  }

  private void logRequest() {
    this.logger.logRequest(this.request);
  }
}
