package com.JavaWebServer.app;

import java.util.ArrayList;

public class ClientWorkerService implements Runnable {
  private final ClientSocket client;
  private final Responder responder;

  public ClientWorkerService (ClientSocket client, Responder responder) {
    this.client = client;
    this.responder = responder;
  }

  public  void run () {
    Request request = client.getRequest();
    client.sendResponse(this.responder.getResponse(request));
    client.close();
    System.out.println ("Server: request Closed");
  }
}
