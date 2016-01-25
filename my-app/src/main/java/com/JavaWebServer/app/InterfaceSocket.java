package com.JavaWebServer.app;
import java.io.*;

public interface InterfaceSocket {
  public InputStream getInputStream();
  public OutputStream getOutputStream(); 
  public void close();
}
