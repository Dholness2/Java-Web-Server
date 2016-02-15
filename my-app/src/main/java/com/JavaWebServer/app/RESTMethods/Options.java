package com.JavaWebServer.app;

public class Options implements RestMethod {
  private String responseStatus;

  public Options(String response) {
    this.responseStatus = response;
  }

  public String handleRequest(Request request) {
    StringBuilder response = new StringBuilder();
    response.append(this.responseStatus + System.lineSeparator());
    response.append("Allow: GET,HEAD,POST,OPTIONS,PUT");
    return response.toString();
  }
}
