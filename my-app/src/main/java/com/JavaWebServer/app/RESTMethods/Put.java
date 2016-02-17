package com.JavaWebServer.app;

public class Put  implements RestMethod {
  private String responseStatus;

  public Put(String response) {
    this.responseStatus = response;
  }

  public byte [] handleRequest(Request request) {
     return this.responseStatus.getBytes();
  }
}
