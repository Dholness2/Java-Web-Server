package com.JavaWebServer.app;
import java.util.Arrays;

public class Request {
  final String  PROTOCOL = "HTTP/1.1";
  final String  ROUTESlASH = "/";
  private String request;

  public Request () {

  }

  public String getRoute() {
    return this.request.split(" ")[1];
  }

  public String getRequest(){
    return this.request.replace(PROTOCOL,"").trim();
  }

  public void setMessage(String message) {
    this.request = message;
  }

  public String getMethod() {
    return this.request.split(" ")[0];
  }

  public boolean validRequest() {
    if (this.request.trim().isEmpty()) {
      return false;
    }else if ((validRoute() == false) ||(validProtocol() == false)) {
      return false;
    } else {
      return true;
    }
  }

  private boolean validRoute() {
    return this.request.split(" ")[1].startsWith("/");
  }

  private boolean validProtocol() {
    return this.request.contains(PROTOCOL);
  }
}
