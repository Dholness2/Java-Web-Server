package com.JavaWebServer.app.serverSockets;

import com.JavaWebServer.app.sockets.Socket;
import com.JavaWebServer.app.sockets.ClientSocket;

import java.io.IOException;

public class ServerSocketWrapper implements ServerSocket {
  private java.net.ServerSocket serverSocket;
  private Socket failedAccept = null;

  public ServerSocketWrapper(int port) {
    try{
      this.serverSocket = new java.net.ServerSocket(port);
    } catch(IOException e) {
      throw new RuntimeException("can not open sever socket"+ e);
    }
  }

  public ServerSocketWrapper (java.net.ServerSocket socket) {
    this.serverSocket = socket;
  }

  public Socket accept() {
    try {
      return new ClientSocket(serverSocket.accept());
    } catch(IOException e) {
      e.printStackTrace();
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
      e.printStackTrace();
    }
  }
}
