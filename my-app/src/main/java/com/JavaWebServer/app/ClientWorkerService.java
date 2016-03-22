package com.JavaWebServer.app;

import java.util.ArrayList;

public class ClientWorkerService implements Runnable {
  private final ClientSocket client;
  private final Responder responder;
  private final Logger logger;
  private Request request;

  public ClientWorkerService (ClientSocket client, Responder responder, Logger logger) {
    this.client = client;
    this.responder = responder;
    this.logger = logger;
  }

  public void run () {
    this.request = client.getRequest();
    logRequest();
    client.sendResponse(getResponse());
    client.close();
    System.out.println ("Server: request Closed");
  }

  private byte [] getResponse() {
    return this.responder.getResponse(this.request);
  }

  private void logRequest() {
    this.logger.logRequest(this.request);
  }
}
