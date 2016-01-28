package com.JavaWebServer.app;


public class App {
  public static void main( String[] args) throws Exception {
    int port = 5000;
    ServerSocket serverSocket = new ServerSocket(port);
    Server app = new Server(port,serverSocket);
    new Thread(app).start();
  }
}
