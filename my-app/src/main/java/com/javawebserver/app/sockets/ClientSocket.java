package com.javawebserver.app.sockets;

import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.ExceptionLogger;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ClientSocket implements Socket {
  private java.net.Socket socket;
  private BufferedReader reader;
  private Request currentRequest;

  public ClientSocket (java.net.Socket socket) {
    this.socket = socket;
  }

  public Request getRequest() {
    try{
      reader = new BufferedReader(getStreamReader());
      currentRequest = new Request();
      updateRequest();
      return currentRequest;
    } catch (IOException e) {
      ExceptionLogger.logException("can't read stream" + e);
    }
    return this.currentRequest;
  }

  public void sendResponse(byte[] response) {
    try {
      OutputStream output = getOutputStream();
      output.write(response);
      output.close();
    } catch  (IOException e)  {
      ExceptionLogger.logException("can't write to stream" + e);
    }
  }

  public void close () {
    try {
      this.socket.close();
    } catch (IOException e) {
      ExceptionLogger.logException("can't close stream" + e);
    }
  }

  private InputStream getInputStream() {
    try {
      return socket.getInputStream();
    } catch(IOException e) {
      ExceptionLogger.logException("can't get Stream" + e);
      return null;
    }
  }

  private OutputStream getOutputStream() {
    try {
      return socket.getOutputStream();
    } catch(IOException e) {
      ExceptionLogger.logException("can't get outputstream" + e);
      return null;
    }
  }

  private InputStreamReader getStreamReader () throws IOException {
    InputStream input = this.socket.getInputStream();
    InputStreamReader stream = new InputStreamReader(input);
    return stream;
  }

  private void updateRequest() throws IOException {
    setRoute();
    setHeaders();
    setBody();
  }

  private boolean hasData () throws IOException {
    return this.reader.ready();
  }

  private void setRoute() throws IOException {
    String route = this.reader.readLine();
    this.currentRequest.setMessage(route);
  }

  private void  setHeaders() throws IOException {
    if (hasData()) {
      StringBuilder header = new StringBuilder();
      String line;
      while (((line = this.reader.readLine()) != null) && !(line.equals(""))){
        header.append(line + System.getProperty("line.separator"));
      }
      this.currentRequest.setHeaders( header.toString());
    }
  }

  private void setBody() throws IOException {
    if (hasData()) {
      StringBuilder body = new StringBuilder();
      int c;
      while ((this.reader.ready()) && ((c = this.reader.read()) != -1)){
        body.append((char)c);
      }
      this.currentRequest.setBody(body.toString());
    }
  }
}
