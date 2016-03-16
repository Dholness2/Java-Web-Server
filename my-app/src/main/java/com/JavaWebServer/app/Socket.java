package com.JavaWebServer.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;



public class Socket implements ClientSocket {
  private java.net.Socket socket;
  private BufferedReader reader;
  private Request currentRequest;

  public Socket (java.net.Socket socket) {
    this.socket = socket;
  }

  public  Request getRequest() {
    try{
      reader = new BufferedReader(getStreamReader());
      currentRequest = new Request();
      updateRequest();
      return currentRequest;
    } catch (IOException e) {
      System.out.println("cant read request"+ e);
      return null;
    }
  }

  public void sendResponse(byte [] response) {
    try {
      OutputStream output = getOutputStream();
      output.write(response);
      output.close();
    } catch  (IOException e)  {
        System.out.println("can't write to ouptustream"+ e);
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
    InputStream input = this.socket.getInputStream();
    InputStreamReader stream = new InputStreamReader(input);
    return stream;
  }

  private void updateRequest() throws IOException {
    this.currentRequest.setMessage(getRoute());
    if (hasData()) {
      this.currentRequest.setHeaders(getHeaders());
    }
    if (hasData()){
      this.currentRequest.setBody(getBody());
    }
  }

  private boolean hasData () throws IOException {
    return this.reader.ready();
  }

  private String getRoute() throws IOException {
    String route = this.reader.readLine();
    return route;
  }

  private String getHeaders() throws IOException {
    StringBuilder header = new StringBuilder();
    String line;
    while (((line = this.reader.readLine()) != null) && !(line.equals(""))){
      header.append(line + System.getProperty("line.separator"));
    }
    return header.toString();
  }

  private String getBody() throws IOException {
    StringBuilder body = new StringBuilder();
    int c;
    while ((this.reader.ready()) && ((c = this.reader.read()) != -1)){
      body.append((char)c);
    }
    return body.toString();
  }
}
