package com.JavaWebServer.app;
import java.net.*;

public class App {
  public static void main( String[] args) throws Exception {
    int port = 5000;
    ServerSocket socket = new ServerSocket(port);
    ServerSocketWrapper serverSocket = new ServerSocketWrapper(socket);
    Server app = new Server(port,serverSocket);
    new Thread(app).start();
  }
}
