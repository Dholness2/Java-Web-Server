package com.JavaWebServer.app;

import java.util.Map;
import java.util.HashMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpResponseBuilder implements ResponseBuilder {
  private String CRLF = System.getProperty("line.separator");
  private byte [] body;
  private StringBuilder responseHeaders = new StringBuilder(); 

  private static final  Map<String, String> statusCodes;
  static {
    statusCodes = new HashMap<String, String>();
    statusCodes.put("OK","HTTP/1.1 200 OK");
    statusCodes.put("NOT_FOUND","HTTP/1.1 404 Not Found");
    statusCodes.put("FOUND", "HTTP/1.1 302 FOUND");
    statusCodes.put("PARTIAL", "HTTP/1.1 206 Partial Content");
    statusCodes.put("RANGE_NOT_SATSIFIABLE","HTTP/1.1 416 Range Not Satisfiable");
    statusCodes.put("STATUSERROR","HTTP/1.1 500 Internal Server Error");
    statusCodes.put("NO_CONTENT","HTTP/1.1 204 NO CONTENT");
    statusCodes.put("UNAUTHORIZED","HTTP/1.1 401 UNAUTHORIZED");
  };

  public HttpResponseBuilder () {}

  public byte [] getResponse(){
   if (body != null){
     return fileResponse();
   }
   return getHeaders();
  }

  private byte [] getHeaders(){
   byte[] response =  this.responseHeaders.toString().getBytes();
   clearBuilder();
   return response;
  }

  private byte [] fileResponse() {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try{
      output.reset();
      output.write(getHeaders());
      output.write((CRLF+CRLF).getBytes());
      output.write(this.body);
      clearBuilder();
      return output.toByteArray();
    } catch (IOException e) {
      new Exception("could not write to Strem").printStackTrace();
      e.printStackTrace();
    }
    return statusCodes.get("STATUSERROR").getBytes();
  }

  public void addHeader(String header, String value) {
    responseHeaders.append(header+value+CRLF);
  }

  public void addBody(byte[] body) {
   this.body = body;
  }

  public void addStatus(String status) {
    String statusHeader = getStatusHeader(status);
    responseHeaders.append(statusHeader + CRLF);
  }

  private void clearBuilder() {
   this.responseHeaders.setLength(0);
   this.responseHeaders.trimToSize();
  }
  private String getStatusHeader(String statusCode){
   return this.statusCodes.get(statusCode);
  }
}
