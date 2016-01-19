package com.JavaWebServer.app;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
  private String serverName = "localhost";
  private int port;
  private boolean serverOn = true;
  private ServerSocket serverSocket = null;
  private Thread runningThread = null;
  protected ExecutorService threadPool = Executors.newFixedThreadPool(10);
 
  public Server (int port) {
   this.port = port;
  }

  public void run () {
    synchronized(this){
    this.runningThread = Thread.currentThread();
    }
    openServerSocket();
    System.out.println( "Server: opened Server Socket");
    while (isServerOn()) {
      Socket clientSocket  = null;
      try {
        clientSocket =  this.serverSocket.accept();
        System.out.println("Server: listening on port");
      } catch (IOException e) {
        if(serverOn == false) {
          System.out.println("Server off "+ e);
          break;
        } throw new RuntimeException( "Error accepting client connection", e);
      }
      this.threadPool.execute(new ClientWorkerService(clientSocket,serverName));
    }
    this.threadPool.shutdown();
    System.out.println("Server off");
  }
 
  private void openServerSocket() {
    try { 
      this.serverSocket = new ServerSocket(port);
       } catch(IOException e) {
         throw new RuntimeException("cannont open port"+ port ,e);
       }
  }

  public synchronized boolean isServerOn() {
    return this.serverOn;
  }
 
  public synchronized void off() {
    this.serverOn = false;
  }
}
