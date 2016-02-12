package com.JavaWebServer.app;

public class Get implements RestMethod {
  private String response;

  public Get (String response) {
    this.response = response;
  }

  public String handleRequest(Request request) {
    return this.response;
  }
}
