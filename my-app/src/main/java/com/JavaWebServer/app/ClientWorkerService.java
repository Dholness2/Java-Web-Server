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
       BufferedReader message = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
       System.out.println(serverName +" Recevied message"+ message.readLine());
       new PrintWriter (output,true).write("HTTP/1.1 200 ok");
       output.close();
       input.close();
       System.out.println ("request Closed");	    
   } catch (IOException e) {
	   e.printStackTrace();
   }
  }
}
