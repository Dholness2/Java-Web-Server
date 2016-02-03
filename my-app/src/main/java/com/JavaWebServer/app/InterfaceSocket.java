package com.JavaWebServer.app;

import java.io.InputStream;
import java.io.OutputStream;

public interface InterfaceSocket {
  public InputStream getInputStream();
  public OutputStream getOutputStream(); 
  public void close();
}
