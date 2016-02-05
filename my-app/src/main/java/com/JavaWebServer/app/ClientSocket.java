package com.JavaWebServer.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.Socket;

public class  ClientSocket implements IClient {

  private Socket socket;

  public ClientSocket (java.net.Socket socket) {
    this.socket = socket;
  }

  public Request getRequest() {
    try{
      BufferedReader request = new BufferedReader(getStreamReader()); 
      return new Request(request.readLine());  
    } catch (IOException e) {
      System.out.println("cant read request"+ e);
      return null;
    }
  }

  public void sendResponse(String r) {
    try {
      PrintWriter responder =  new PrintWriter(socket.getOutputStream(),true);
      responder.println(r);
    } catch (IOException e) {
      System.out.println("Can't write to outputStream" + e);
    }
  }

  public void close () {
    try {
      this.socket.close();
    } catch (IOException e) {
      System.out.println("Can't Close Client Socket" + e);
    }
  } 


  private InputStream getInputStream() {
    try { 
      return socket.getInputStream();
    } catch(IOException e) {
      System.out.println("can't open InputStream"+ e);
      return null;
    }
  }

  private OutputStream getOutputStream() {
    try {
      return socket.getOutputStream();
    } catch(IOException e) {
      System.out.println("can't open OuputStream"+ e);
      return null;
    }
  }

  private InputStreamReader getStreamReader () throws IOException {
    InputStreamReader stream = null;
    InputStream input = this.socket.getInputStream();
    stream = new InputStreamReader(input);
    return stream;
  }
}
