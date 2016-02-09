package com.JavaWebServer.app;

public class Request {
  private String request;

  public Request () {

  }

  public String getRoute() {
     return this.request.split(" ")[1];
  }

  public void setMessage(String message) {
    this.request = message;
  }

  public String getMethod() {
    return this.request.split(" ")[0];
  }
}
