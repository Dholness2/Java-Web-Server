package com.JavaWebServer.app;
public interface ClientSocket {
  public Request getRequest();
  public void sendResponse(String r );
  public void close();
}
