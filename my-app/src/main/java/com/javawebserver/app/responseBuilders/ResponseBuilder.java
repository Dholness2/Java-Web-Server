package com.javawebserver.app.responseBuilders;

public interface ResponseBuilder {
  public ResponseBuilder clone();
  public byte[] getStatus(String status);
  public byte[] getResponse();
  public void addHeader(String header, String headerValue);
  public void addBody(byte[] body);
  public void addStatus(String status);
  public void clearBuilder();
}
