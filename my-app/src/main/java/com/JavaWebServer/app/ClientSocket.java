package com.JavaWebServer.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class  ClientSocket implements IClient {

  private java.net.Socket wrapper;

  public ClientSocket (java.net.Socket socket) {
    this.wrapper = socket;
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

 public Response sendResponse(Response r) {
    return new Response();

 }

  public InputStream  getInputStream() {
    try { 
      return wrapper.getInputStream();
         } catch(IOException e) {
           System.out.println("can't open InputStream"+ e);
           return null;
      }
  }

  public OutputStream getOutputStream() {
    try {
      return wrapper.getOutputStream();
        } catch(IOException e) {
          System.out.println("can't open OuputStream"+ e);
         return null;
        }
  }

  
  private  InputStreamReader getStreamReader () throws IOException {
    InputStreamReader stream = null;
    InputStream input = this.wrapper.getInputStream();
    stream = new InputStreamReader(input);
    return stream;
  }

 public boolean isClosed() {
    return wrapper.isClosed();
  }

  public void close() {
    try {
       wrapper.close();
        } catch(IOException e) {
          System.out.println("can't close socket"+ e);
      }
  }
 }
