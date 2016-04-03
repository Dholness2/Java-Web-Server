package com.javawebserver.app.serverSockets;

import com.javawebserver.app.sockets.Socket;

public interface ServerSocket {
  public Socket accept();
  public void close();
  public boolean isClosed();
}
