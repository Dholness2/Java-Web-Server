package com.JavaWebServer.app;
public interface IClient {
  public Request getRequest();
  public void sendResponse(String r );
  public void close();
}
