package com.JavaWebServer.app;
import java.net.*;
import java.io.*;

public class ServerSocketWrapper implements InterfaceServerSocket {
  private ServerSocket serverSocket;

  public ServerSocketWrapper(ServerSocket socket) {
    this.serverSocket = socket;
  }

  public WrapperSocket accept() {
    try {
      return new WrapperSocket(serverSocket.accept());
        } catch(IOException e) {
          throw new RuntimeException("can not accept Client Connnection"+ e);
       }
  }

  public Boolean isClosed() {
    return serverSocket.isClosed();
  }

  public void close() {
    try {
      serverSocket.close();
        } catch(IOException e) {
          throw new RuntimeException("can not close ServerSocket"+ e);
       }
  }
}
