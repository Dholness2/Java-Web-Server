package com.JavaWebServer.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
  private int port;
  private boolean serverOn = true;
  private ServerSocket serverSocket;
  private Responder responder;
  private ClientSocket clientSocket;
  private Logger logger;
  protected ExecutorService threadPool = Executors.newFixedThreadPool(98);

  public Server (int port, ServerSocket socket, Responder responder, Logger logger) {
    this.port = port;
    this.serverSocket = socket;
    this.responder = responder;
    this.logger = logger;
  }

  public  void run () {
    while (isServerOn()) {
      clientSocket = serverSocket.accept();
      System.out.println("Server: listening on port");
      this.threadPool.execute(new ClientWorkerService(this.clientSocket,this.responder, this.logger));
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
