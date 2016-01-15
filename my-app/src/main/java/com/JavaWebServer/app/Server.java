package com.JavaWebServer.app;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable{

 private int port;
 private boolean serverOn = true;
 private ServerSocket serverSocket = null;
 private Thread runningThread = null;
 public Server (int port) {
   this.port = port;
 }

 public void  run () {
   synchronized(this){
    this.runningThread = Thread.currentThread();
   }
   openServerSocket();
   System.out.println( "opened Server Socket");
   while (isServerOn()){
     System.out.println("inside while");
     Socket clientSocket  = null;
     try {
       clientSocket =  this.serverSocket.accept();
       System.out.println("listening on port");
     } catch (IOException e) {
        if(serverOn == false) {
          System.out.println("Server off "+ e);
          return;
        } 
        throw new RuntimeException(
          "Error accepting client connection", e);
     } 
    try {
        serveClient(clientSocket);
    } catch (IOException e) {
    } 
   }
   System.out.println("Server off");
 }
 
 private void openServerSocket() {
  try { 
      this.serverSocket = new ServerSocket(port);
      } catch(IOException e) {
        throw new RuntimeException("cannont open port"+ port ,e);
      }
 }

 private void serveClient (Socket clientSocket) 
  throws IOException {
   PrintWriter writer  = new PrintWriter(clientSocket.getOutputStream());
    writer.println("200");
    writer.close();
    System.out.println("message sent from sever");
    System.out.println("session completed");
    clientSocket.close();
   }

 private synchronized boolean isServerOn() {
  return this.serverOn;
 }


 public synchronized void off() {
   this.serverOn = false;
   try {
     this.serverSocket.close();
   } catch (IOException e) {
   throw new RuntimeException("Error closing server", e);
   }
 }
}
