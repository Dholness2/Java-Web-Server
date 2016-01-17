package com.JavaWebServer.app;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientWorkerService implements Runnable {
  protected Socket clientSocket = null;
  protected String serverName = null;
  
  public ClientWorkerService (Socket clientSocket, String serverName) {
	  this.clientSocket = clientSocket;
	  this.serverName = serverName;
  }

  public void run() {
   try {
       InputStream input = clientSocket.getInputStream();
       OutputStream output = clientSocket.getOutputStream();
       System.out.println("Server: reading inputstream");
       BufferedReader message = new BufferedReader(new InputStreamReader(input));
       System.out.println(serverName +" Recevied message"+ message.readLine());
       new PrintWriter (output,true).println("HTTP/1.1 200 ok");
       output.close();
       input.close();
       clientSocket.close();
       System.out.println ("request Closed");	    
   } catch (IOException e) {
	   e.printStackTrace();
   }
  }
}
