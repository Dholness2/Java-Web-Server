package com.JavaWebServer.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
  private String serverName = "localhost";
  private int port;
  private boolean serverOn = true;
  private ServerSocket serverSocket;
  private final  Responder responder;
  private  ClientSocket clientSocket;
  protected ExecutorService threadPool = Executors.newFixedThreadPool(98);

  public Server (int port, ServerSocket socket, Responder responder) {
    this.port = port;
    this.serverSocket = socket;
    this.responder = responder;
  }

  public  void run () {
    while (isServerOn()) {
      clientSocket = serverSocket.accept();
      System.out.println("Server: listening on port");
      this.threadPool.execute(new ClientWorkerService(clientSocket,responder));
    }
    closeThreadPool();
    System.out.println("Server off");
  }

  public  boolean isServerOn() {
    return this.serverOn;
  }

  public  void off() {
    this.serverOn = false;
  }

  private void closeThreadPool () {
    this.threadPool.shutdown();
  }
}
