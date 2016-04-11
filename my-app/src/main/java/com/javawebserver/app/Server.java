package com.javawebserver.app;

import com.javawebserver.app.workers.Worker;
import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.serverSockets.ServerSocket;
import com.javawebserver.app.helpers.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
  private Worker worker;
  private boolean serverOn = true;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  protected ExecutorService threadPool = Executors.newFixedThreadPool(120);

  public Server(Worker worker, ServerSocket socket) {
    this.worker = worker;
    this.serverSocket = socket;
  }

  public void run() {
    while (isServerOn()) {
      clientSocket = serverSocket.accept();
      Worker currentWorker = this.worker.clone();
      currentWorker.setSocket(clientSocket);
      this.threadPool.execute(currentWorker);
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
