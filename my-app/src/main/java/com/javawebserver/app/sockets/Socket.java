package com.javawebserver.app.sockets;

import com.javawebserver.app.Request;

public interface Socket {
  public Request getRequest();
  public void sendResponse(byte[] response);
  public void close();
}
