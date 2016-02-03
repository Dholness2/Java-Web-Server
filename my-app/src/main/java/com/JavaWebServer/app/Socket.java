package com.JavaWebServer.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class  Socket implements InterfaceSocket {

  private java.net.Socket wrapper;

  public Socket (java.net.Socket socket) {
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
