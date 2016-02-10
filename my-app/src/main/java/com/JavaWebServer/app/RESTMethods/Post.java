package com.JavaWebServer.app;

public class Post implements RestMethod {
  private final String METHODKEY = "POST";
  private final String ACCEPTED ="HTTP/1.1 200 ok";
  private RestMethod next;

  public Post() { }

  public void setNextMethod(RestMethod method) {
    this.next = method;
  }

  public String handleRequest(Request request) {
    if ((request.getMethod()).equals(METHODKEY)) {
      return ACCEPTED;
    } else {
      return "HTTP/1.1 400 bad";
    }
  }
}
