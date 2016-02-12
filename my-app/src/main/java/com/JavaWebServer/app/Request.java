package com.JavaWebServer.app;
import java.util.Arrays;

public class Request {
  private String request;

  public Request () {

  }

  public String getRoute() {
    return this.request.split(" ")[1];
  }

  public String getRequest(){
    return this.request.replace("HTTP/1.1","").trim();
  }

  public void setMessage(String message) {
    this.request = message;
  }

  public String getMethod() {
    return this.request.split(" ")[0];
  }
}
