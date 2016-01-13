package com.JavaWebServer.app;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable{

 private int port;
 private boolean on = false;
 
 public Server (int port) {
   this.port = port;
 }

 public void  openSocket (int port) {
  System.out.println(port);
   try{
    ServerSocket socket = new ServerSocket (port);
    System.out.println(socket);
    while (true){
     Socket clientSocket = socket.accept();
     
     PrintWriter writer  = new PrintWriter(clientSocket.getOutputStream());
     writer.println("200");
     writer.close();
     System.out.println("message sent from sever");
    }
   } catch (IOException e) {
   System.out.println("Exception caught "+ port + " or listening for a connection");
   System.out.println(e.getMessage());
  }
 }

 public void run() {
  this.on = true;
  System.out.println("sever is on");
  openSocket(port);
  System.out.println("sever on and running");
 }
 
 public void off() {
   this.on = false;
 }
}
