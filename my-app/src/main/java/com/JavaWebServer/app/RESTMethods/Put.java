package com.JavaWebServer.app;

public class Put  implements RestMethod {
  private final String METHODKEY= "PUT";
  private final String ACCEPTED ="HTTP/1.1 200 ok";
  private RestMethod next;

  public Put() {}

  public void setNextMethod(RestMethod method) {
    this.next = method;
  }

  public String handleRequest(Request request) {
    if ((request.getMethod()).equals(METHODKEY)) {
      return ACCEPTED;
    } else {
      return this.next.handleRequest(request);
    }
  }
}
