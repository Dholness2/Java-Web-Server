package com.JavaWebServer.app;

public class Head  implements RestMethod {
  private String responseStatus;

  public Head(String response ) {
    this.responseStatus = response;
  }

  public String handleRequest(Request request) {
      return this.responseStatus;
  }
}
