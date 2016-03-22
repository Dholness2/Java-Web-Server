package com.JavaWebServer.app;

import java.io.IOException;

public class ServerSocket implements InterfaceServerSocket {
  private java.net.ServerSocket serverSocket;
  private Socket failedAccept = null;
  
  public ServerSocket(int port) {
    try{
      this.serverSocket = new java.net.ServerSocket(port);
    } catch(IOException e) {
      throw new RuntimeException("can not open sever socket"+ e);
    }
  }

  public ServerSocket (java.net.ServerSocket socket) {
    this.serverSocket = socket;
  }

  public Socket accept() {
    try {
      return new Socket(serverSocket.accept());
    } catch(IOException e) {
      new Exception("Can not accept Client Connection").printStackTrace();
      e.printStackTrace();
    }
    return failedAccept;
  }

  public Boolean isClosed() {
    return serverSocket.isClosed();
  }

  public void close() {
    try {
      serverSocket.close();
    } catch(IOException e) {
      new Exception("Can not close ServerSocket").printStackTrace();
      e.printStackTrace();
    }
  }
}
