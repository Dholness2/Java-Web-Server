package com.JavaWebServer.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
  private String serverName = "localhost";
  private int port;
  private boolean serverOn = true;
  private ServerSocket serverSocket = null;
  protected ExecutorService threadPool = Executors.newFixedThreadPool(10);

  public Server (int port, ServerSocket socket) {
    this.port = port;
    this.serverSocket = socket;
  }

  public void run () {
    while (isServerOn()) {
      Socket clientSocket  = null;
      clientSocket = serverSocket.accept();
      System.out.println("Server: listening on port");
      this.threadPool.execute(new ClientWorkerService(clientSocket,serverName));
    }
    closeThreadPool();
    System.out.println("Server off");
  }

  public synchronized boolean isServerOn() {
    return this.serverOn;
  }

  public synchronized void off() {
    this.serverOn = false;
  }

  private void closeThreadPool () {
    this.threadPool.shutdown();
  }
}
