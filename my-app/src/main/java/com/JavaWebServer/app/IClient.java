package com.JavaWebServer.app;
public interface IClient {
  public Request getRequest();
  public Response sendResponse(Response r );
}
