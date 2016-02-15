package com.JavaWebServer.app;

public class Options implements RestMethod {
  private String responseStatus;

  public Options(String response) {
    this.responseStatus = response;
  }

  public String handleRequest(Request request) {
    return (this.responseStatus + System.lineSeparator() + "Allow: GET,HEAD,POST,OPTIONS,PUT");
  }
}
