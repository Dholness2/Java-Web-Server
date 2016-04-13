package com.javawebserver.app.serverSockets;

import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.sockets.ClientSocket;
import com.javawebserver.app.helpers.ExceptionLogger;

import java.io.IOException;

public class ServerSocketWrapper implements ServerSocket {
  private java.net.ServerSocket serverSocket;
  private Socket failedAccept = null;

  public ServerSocketWrapper(int port) throws IOException {
    this.serverSocket = new java.net.ServerSocket(port);
  }

  public ServerSocketWrapper (java.net.ServerSocket socket) {
    this.serverSocket = socket;
  }

  public Socket accept() {
    try {
      return new ClientSocket(serverSocket.accept());
    } catch(IOException e) {
      ExceptionLogger.logException("can't accept request"+ e);
    }
    return failedAccept;
  }

  public boolean isClosed() {
    return serverSocket.isClosed();
  }

  public void close() {
    try {
      serverSocket.close();
    } catch(IOException e) {
      ExceptionLogger.logException("can not close Server Socket" + e);
    }
  }
}
