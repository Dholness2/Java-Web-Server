package com.JavaWebServer.app.serverSockets;

import com.JavaWebServer.app.sockets.Socket;

public interface ServerSocket {
  public Socket accept();
  public void close();
  public boolean isClosed();
}
