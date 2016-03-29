package com.JavaWebServer.app.sockets;

import com.JavaWebServer.app.Request;

public interface Socket {
  public Request getRequest();
  public void sendResponse(byte [] response);
  public void close();
}
