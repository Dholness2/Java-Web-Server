package com.JavaWebServer.app;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class ClientWorkerService implements Runnable {
  private ClientSocket clientSocket = null;
  private String serverName = null;

  public ClientWorkerService (ClientSocket clientSocket, String serverName) {
    this.clientSocket = clientSocket;
    this.serverName = serverName;
  }

  public void run () {
     Request  request = clientSocket.getRequest();
     PrintWriter response = new PrintWriter (getOutputStream(),true);

     if (pathExists(request)) {
       response.println("HTTP/1.1 200 ok"+"\n");
       response.println("Hello world");
     } else {
       response.println("HTTP/1.1 404 not found"+"\n");
     }

     clientSocket.close();
     System.out.println ("Server: request Closed");
  }

  public boolean pathExists(Request r) {
    String route = r.getRoute();
    return (route.equals( "/"));
  }

  public OutputStream getOutputStream () {
    return clientSocket.getOutputStream();
  }
}
