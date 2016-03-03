package com.JavaWebServer.app;

import java.util.HashMap;
import java.util.Map;

 public class Params implements RestMethod {
  private String status;
  private String params;
  private Request request;
  private Map <String, String> encodingKey;
  private String CRLF ="\r\n";
  private String typeHeader = "Content-Type: ";
  private String contentLength = "Content-Length:";

  public Params (String status, String params, Map <String, String> encodingKey) {
    this.status = status;
    this.params = params;
    this.encodingKey= encodingKey;
  }

  public byte [] handleRequest (Request request) {
    this.request = request;
    String body = decodeParams();
    int size = body.getBytes().length;
    String response = (status+CRLF+typeHeader+"text/plain"+CRLF+contentLength+size+CRLF+body);
    return response.getBytes();
  }

  private String decodeParams () {
    String [] variables =  this.request.getParams().split("&");
    int length = variables.length;
    for (int index = 0; index < length; index++) {
      variables[index] = CRLF+Decoder.decode(encodingKey,variables[index]);
    }
    return buildString(variables);
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
