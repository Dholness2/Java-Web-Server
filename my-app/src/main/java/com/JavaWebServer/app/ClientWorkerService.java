package com.JavaWebServer.app;

public class ClientWorkerService implements Runnable {
  private IClient client = null;
  private String serverName = null;

  public ClientWorkerService (IClient client, String serverName) {
    this.client = client;
    this.serverName = serverName;
  }

  public void run () {
    Request request = client.getRequest();
    if (pathExists(request)) {
      client.sendResponse("HTTP/1.1 200 ok"+"\n");
      client.sendResponse("Hello world");
    } else {
      client.sendResponse("HTTP/1.1 404 not found"+"\n");
      client.sendResponse("404");
    }
    client.close();
    System.out.println ("Server: request Closed");
  }

  private boolean pathExists(Request r) {
    String route = r.getRoute();
    return (route.equals( "/"));
  }
}
