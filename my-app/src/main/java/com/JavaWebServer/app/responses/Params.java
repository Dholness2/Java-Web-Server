package com.JavaWebServer.app.responses;

import com.JavaWebServer.app.decoders.ParamsDecoder;
import com.JavaWebServer.app.Request;
import com.JavaWebServer.app.responses.RestMethod;

import java.util.HashMap;
import java.util.Map;

 public class Params implements RestMethod {
  private String status;
  private String params;
  private Request request;
  private String CRLF ="\r\n";
  private String typeHeader = "Content-Type: ";
  private String contentLength = "Content-Length:";

  public Params (String status, String params) {
    this.status = status;
    this.params = params;
  }

  public byte [] handleRequest (Request request) {
    this.request = request;
    String body = decodeParams();
    int size = body.getBytes().length;
    String response = (status+CRLF+typeHeader+"text/plain"+CRLF+contentLength+size+CRLF+CRLF+body);
    return response.getBytes();
  }

  private String decodeParams () {
    String [] variables =  this.request.getParams().split("&");
    int length = variables.length;
    for (int index = 0; index < length; index++) {
      variables[index] = ParamsDecoder.decode(variables[index])+CRLF;
    }
    return buildString(variables).trim();
  }

  private String buildString(String [] stringArray) {
    StringBuilder strBuilder = new StringBuilder();
    int size = stringArray.length;
    for (int i = 0; i<size; i++) {
      strBuilder.append(stringArray[i]);
    }
    return  strBuilder.toString();
  }
}
