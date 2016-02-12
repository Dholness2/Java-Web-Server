package com.JavaWebServer.app;

public class Head  implements RestMethod {
  private final String METHODKEY= "HEAD";
  private final String ACCEPTED ="HTTP/1.1 200 ok";

  public Head() {}

  public String handleRequest(Request request) {
      return ACCEPTED;
  }
}
