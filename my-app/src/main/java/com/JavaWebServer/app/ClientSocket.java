package com.JavaWebServer.app;

public interface ClientSocket {
  public Request getRequest();
  public void sendResponse(byte [] response);
  public void close();
}
