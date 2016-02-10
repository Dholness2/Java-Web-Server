package com.JavaWebServer.app;

public class Options implements RestMethod {
  private final String METHODKEY = "OPTIONS";
  private final String ACCEPTED ="HTTP/1.1 200 ok";
  private RestMethod next;

  public Options() { }

  public void setNextMethod(RestMethod method) {
    this.next = method;
  }

  public String handleRequest(Request request) {
    if ((request.getMethod()).equals(METHODKEY)) {
      return (ACCEPTED + System.lineSeparator() + "Allow: GET,HEAD,POST,OPTIONS,PUT");
    } else {
      return this.next.handleRequest(request);
    }
  }
}
