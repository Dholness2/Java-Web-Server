package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.Request;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.decoders.ParamsDecoder;

import java.util.HashMap;
import java.util.Map;

public class Params implements Response {
  private ResponseBuilder response;
  private String params;
  private Request request;
  private static final String CRLF = System.getProperty("line.separator");
  private static final String TYPE_HEADER = "Content-Type: ";
  private static final String CONTENT_LENGTH = "Content-Length: ";

  public Params (ResponseBuilder response, String params) {
    this.response = response;
    this.params = params;
  }

  public byte[] handleRequest(Request request) {
    this.request = request;
    byte [] response = getResponse();
    this.response.clearBuilder();
    return response;
  }

  private byte[] getResponse() {
    byte[] responseBody = decodeParams().getBytes();
    this.response.addStatus("OK");
    this.response.addHeader(TYPE_HEADER,"text/plain");
    this.response.addHeader(CONTENT_LENGTH, Integer.toString(responseBody.length));
    this.response.addBody(responseBody);
    return response.getResponse();
  }

  private String decodeParams() {
    HashMap<String, String> paramKey = getParamKey();
    String[] variables = getParamVariables();
    decodeVariables(variables);
    return buildParamsString(variables).trim();
  }

  private String[] getParamVariables() {
    String params = this.request.getParams();
    params = params.replaceAll("=", " = ");
    return params.split("&");
  }

  private void decodeVariables(String[] variables) {
    for (int index = 0; index < variables.length; index++) {
      variables[index] = ParamsDecoder.decodeParams(getParamKey(), variables[index]);
    }
  }

  private String buildParamsString(String[] params) {
    StringBuilder strBuilder = new StringBuilder();
    for (String decodedParam: params){
      strBuilder.append(decodedParam + CRLF);
    }
    return  strBuilder.toString();
  }

  private static HashMap <String, String>  getParamKey() {
    HashMap<String, String> encode = new HashMap<String, String>();
    encode.put("%3C","<");
    encode.put("%3E",">");
    encode.put("%3D","=");
    encode.put("%2C",",");
    encode.put("%21","!" );
    encode.put("%2B","+");
    encode.put("%2D","-");
    encode.put("%20"," ");
    encode.put("%22", "\"");
    encode.put("%3B",";");
    encode.put("%2A","*");
    encode.put("%26","&");
    encode.put("%40","@");
    encode.put("%23","#");
    encode.put("%24","\\$");
    encode.put("%5B","[");
    encode.put("%5D","]");
    encode.put("%3A",":");
    encode.put("%3F","?");
    return encode;
  }
}
