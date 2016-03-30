package com.JavaWebServer.app.responseBuilders;

import com.JavaWebServer.app.responseBuilders.ResponseBuilder;

import java.util.Map;
import java.util.HashMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpResponseBuilder implements ResponseBuilder {
  private StringBuilder headerBuilder = new StringBuilder();
  private String CRLF = System.getProperty("line.separator");
  private byte [] body;

  private static final  Map<String, String> statusCodes;
  static {
    statusCodes = new HashMap<String, String>();
    statusCodes.put("OK","HTTP/1.1 200 OK");
    statusCodes.put("NOT_FOUND","HTTP/1.1 404 Not Found");
    statusCodes.put("FOUND", "HTTP/1.1 302 Found");
    statusCodes.put("PARTIAL", "HTTP/1.1 206 Partial Content");
    statusCodes.put("RANGE_NOT_SATISFIABILITY","HTTP/1.1 416 Range Not Satisfiable");
    statusCodes.put("STATUSERROR","HTTP/1.1 500 Internal Server Error");
    statusCodes.put("NO_CONTENT","HTTP/1.1 204 No Content");
    statusCodes.put("UNAUTHORIZED","HTTP/1.1 401 Unauthorized");
  };

  public HttpResponseBuilder () {}

  public byte [] getResponse(){
    if (body != null){
      return fileResponse();
    }
    return getHeaders();
  }

  private byte [] getHeaders(){
    byte[] response =  this.headerBuilder.toString().getBytes();
    return response;
  }

  private byte [] fileResponse() {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try{
      output.reset();
      output.write(getHeaders());
      output.write((CRLF).getBytes());
      output.write(this.body);
      return output.toByteArray();
    } catch (IOException e) {
      new Exception("could not write to Strem").printStackTrace();
      e.printStackTrace();
    }
    return statusCodes.get("STATUSERROR").getBytes();
  }

  public void addHeader(String header, String value) {
    headerBuilder.append(header+value+CRLF);
  }

  public void addBody(byte[] body) {
    this.body = body;
  }

  public void addStatus(String status) {
    String statusHeader = getStatusHeader(status);
    headerBuilder.append(statusHeader + CRLF);
  }

  public void clearBuilder() {
    this.headerBuilder.delete(0, this.headerBuilder.length());
    this.body = null;
  }

  private String getStatusHeader(String statusCode){
    return this.statusCodes.get(statusCode);
  }
}
