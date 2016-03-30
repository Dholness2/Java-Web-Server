package com.JavaWebServer.app;

public interface ResponseBuilder {
  public byte [] getResponse();
  public void addHeader(String header,String headerValue);
  public void addBody (byte [] body);
  public void addStatus(String status);
  public void clearBuilder();
}
