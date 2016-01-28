package com.JavaWebServer.app;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientWorkerService implements Runnable {
  private Socket clientSocket = null;
  private String serverName = null; 

  public ClientWorkerService (Socket clientSocket, String serverName) {
    this.clientSocket = clientSocket;
    this.serverName = serverName;
  }

  public void run () {
     BufferedReader message = new BufferedReader(getStreamReader());
     System.out.println(serverName +" Recevied message");
     PrintWriter response = new PrintWriter (getOutputStream(),true);
     response.println("HTTP/1.1 200 ok"+"\n");
     response.println("Hello world");
     clientSocket.close();
     System.out.println ("Server: request Closed");
  }
  
  public InputStreamReader getStreamReader () {
    InputStreamReader stream = null;
    InputStream input = clientSocket.getInputStream();
    stream = new InputStreamReader(input);
    return stream;
  }
  
  public OutputStream getOutputStream () {
    return clientSocket.getOutputStream();
  }
}
