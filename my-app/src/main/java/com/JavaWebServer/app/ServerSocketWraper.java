package com.JavaWebServer.app;
import java.net.*;
import java.io.*;

public class ServerSocketWraper implements InterfaceServerSocket {
  private ServerSocket serverSocket;
  
  public ServerSocketWraper(ServerSocket socket) {
    this.serverSocket = socket;
  }
   
  public WraperSocket accept() {
    try { 
      return new WraperSocket(serverSocket.accept());
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
