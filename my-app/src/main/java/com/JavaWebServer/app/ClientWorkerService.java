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
  private Socket clientSocket = null;
  private String serverName = null;

  public ClientWorkerService (Socket clientSocket, String serverName) {
    this.clientSocket = clientSocket;
    this.serverName = serverName;
  }

  public void run () {
     BufferedReader message = new BufferedReader(getStreamReader());
     PrintWriter response = new PrintWriter (getOutputStream(),true);

     if (pathExists(message)) {
       response.println("HTTP/1.1 200 ok"+"\n");
       response.println("Hello world");
     } else {
       response.println("HTTP/1.1 404 not found"+"\n");
     }

     clientSocket.close();
     System.out.println ("Server: request Closed");
  }

  public boolean pathExists(BufferedReader message) {
    String request = null;
    try {
      request = message.readLine();
      System.out.println("request: " + request);
    } catch (IOException e) {
      System.out.println( e);
    }
    
    String[] requestParts = request.split(" ");
    System.out.println(Arrays.toString(requestParts));
    String route = requestParts[1];
    boolean ans = (route.equals( "/"));
    System.out.println(ans);
    return ans;
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
