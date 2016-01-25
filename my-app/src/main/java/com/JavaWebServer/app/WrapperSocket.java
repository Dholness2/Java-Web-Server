package com.JavaWebServer.app;
import java.net.*;
import java.io.*;

public class  WrapperSocket implements InterfaceSocket {

  private Socket wrapper;

  public WrapperSocket (Socket socket) {
    this.wrapper = socket;
  }

  public InputStream  getInputStream() {
    try { 
      return wrapper.getInputStream();
         } catch(IOException e) {
           throw new RuntimeException("can't open InputStream"+ e);
      }
  }

  public OutputStream getOutputStream() {
    try {
      return wrapper.getOutputStream();
        } catch(IOException e) {
          throw new RuntimeException("can't open OuputStream"+ e);
      }
  }

  public boolean isClosed() {
    return wrapper.isClosed();
  }

  public void close() {
    try {
       wrapper.close();
        } catch(IOException e) {
          throw new RuntimeException("can't close socket"+ e);
      }
  }
 }
