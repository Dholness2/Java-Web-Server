package com.JavaWebServer.app;

public class Options implements RestMethod {
  private final String METHODKEY = "OPTIONS";
  private final String ACCEPTED ="HTTP/1.1 200 ok";

  public Options() { }

  public String handleRequest(Request request) {
    return (ACCEPTED + System.lineSeparator() + "Allow: GET,HEAD,POST,OPTIONS,PUT");
  }
}
