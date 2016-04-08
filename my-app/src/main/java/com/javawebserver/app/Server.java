package com.javawebserver.app;

import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.serverSockets.ServerSocket;
import com.javawebserver.app.helpers.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
  private int port;
  private boolean serverOn = true;
  private ServerSocket serverSocket;
  private Responder responder;
  private Socket clientSocket;
  private Logger logger;
  protected ExecutorService threadPool = Executors.newFixedThreadPool(120);

  public Server (int port, ServerSocket socket, Responder responder, Logger logger) {
    this.port = port;
    this.serverSocket = socket;
    this.responder = responder;
    this.logger = logger;
  }

  public  void run () {
    while (isServerOn()) {
      clientSocket = serverSocket.accept();
      this.threadPool.execute(new ClientWorkerService(this.clientSocket,this.responder, this.logger));
    }
    closeThreadPool();
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
