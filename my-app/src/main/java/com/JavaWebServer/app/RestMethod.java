package com.JavaWebServer.app;

public interface RestMethod {
  public String handleRequest(Request request);
  public void setNextMethod(RestMethod method);
}
