package com.JavaWebServer.app;

public class GetMethod implements RestMethod {
  private final String ACCEPTED ="HTTP/1.1 200 ok";
  private RestMethod next;

  public GetMethod() { }

  public void setNextMethod(RestMethod method) {
    this.next = method;
  }

  public String handleRequest(Request request) {
    if ((request.getMethod()).equals("GET")) {
      return ACCEPTED;
    } else {
      return next.handleRequest(request);
    }
  }
}
