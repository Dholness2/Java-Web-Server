package com.JavaWebServer.app;

public class Post implements RestMethod {
  private final String METHODKEY = "POST";
  private final String ACCEPTED ="HTTP/1.1 200 ok";

  public Post() { }

  public String handleRequest(Request request) {
    if ((request.getMethod()).equals(METHODKEY)) {
      System.out.println(request.getRequest());
      return ACCEPTED;
    } else {
      return "HTTP/1.1 400 bad";
    }
  }
}
