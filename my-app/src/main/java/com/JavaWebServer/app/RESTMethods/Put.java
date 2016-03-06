package com.JavaWebServer.app;

public class Put  implements RestMethod {
  private String responseStatus;

  public Put(String response) {
    this.responseStatus = response;
  }

  public byte [] handleRequest() {
     return this.responseStatus.getBytes();
  }
}
