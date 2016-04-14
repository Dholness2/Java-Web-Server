package com.javawebserver.app.responseBuilders;

import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.helpers.ExceptionLogger;

import java.util.Map;
import java.util.HashMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpResponseBuilder implements ResponseBuilder {
  private StringBuilder headerBuilder = new StringBuilder();
  private String CRLF = System.getProperty("line.separator");
  private byte[] body;

  private static final  Map<String, String> statusCodes;
  static {
    statusCodes = new HashMap<String, String>();
    statusCodes.put("200", "HTTP/1.1 200 OK");
    statusCodes.put("404", "HTTP/1.1 404 Not Found");
    statusCodes.put("302", "HTTP/1.1 302 Found");
    statusCodes.put("206", "HTTP/1.1 206 Partial Content");
    statusCodes.put("416", "HTTP/1.1 416 Range Not Satisfiable");
    statusCodes.put("500", "HTTP/1.1 500 Internal Server Error");
    statusCodes.put("204", "HTTP/1.1 204 No Content");
    statusCodes.put("401", "HTTP/1.1 401 Unauthorized");
    statusCodes.put("400", "HTTP/1.1 400 Bad Request");
    statusCodes.put("405", "HTTP/1.1 405 Method Not Allowed");
  };

  public HttpResponseBuilder() {}

  public ResponseBuilder clone() {
    return new HttpResponseBuilder();
  }

  public byte[] getStatus(String statusCode) {
    return this.statusCodes.get(statusCode).getBytes();
  }

  public byte [] getResponse() {
    if (body != null){
      return fileResponse();
    }
    return getHeaders();
  }

  public void addHeader(String header, String value) {
    headerBuilder.append(header + value + CRLF);
  }

  public void addBody(byte[] body) {
    this.body = body;
  }

  public void addStatus(String statusCode) {
    String statusHeader = getStatusHeader(statusCode);
    headerBuilder.append(statusHeader + CRLF);
  }

  private byte[] getHeaders() {
    byte[] response = this.headerBuilder.toString().getBytes();
    return response;
  }

  private byte[] fileResponse() {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try {
      output.reset();
      output.write(getHeaders());
      output.write((CRLF).getBytes());
      output.write(this.body);
      return output.toByteArray();
    } catch (IOException e) {
      ExceptionLogger.logException("can not write to stream" + e);
    }
    return getStatus("500");
  }

  public void clearBuilder() {
    this.headerBuilder.delete(0, this.headerBuilder.length());
    this.body = null;
  }

  private String getStatusHeader(String statusCode) {
    return this.statusCodes.get(statusCode);
  }
}
