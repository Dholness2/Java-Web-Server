package com.JavaWebServer.app;

import java.util.ArrayList;

public class ClientWorkerService implements Runnable {
  private ClientSocket client;
  private Responder responder;

  public ClientWorkerService (ClientSocket client, Responder responder) {
    this.client = client;
    this.responder = responder;
  }

  public void run () {
    Request request = client.getRequest();
    client.sendResponse(responder.getResponse(request));
    client.close();
    System.out.println ("Server: request Closed");
  }
}
