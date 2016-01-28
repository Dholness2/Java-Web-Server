package com.JavaWebServer.app;

import java.io.*;

public class ServerSocket implements InterfaceServerSocket {
  private java.net.ServerSocket serverSocket;

  public ServerSocket(int port) {
    try{
      this.serverSocket = new java.net.ServerSocket(port);
       } catch(IOException e) {
         throw new RuntimeException("can not open sever socket"+ e);
       }
  }

  public Socket accept() {
    try {
      return new Socket(serverSocket.accept());
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
