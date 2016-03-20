package com.JavaWebServer.app;

import java.util.HashMap;
import java.util.ArrayList;

public class Options implements RestMethod {
  private String status;
  private String allowHeader= "Allow: ";
  private HashMap<String,ArrayList<String>> directory;
  private String CRLF = System.getProperty("line.separator");
  
  
  public Options(String response,HashMap<String,ArrayList<String>> directory ) {
    this.status = response;
    this. directory = directory;
  }

  public byte [] handleRequest(Request request) {
    ArrayList methods = getAllowedMethods(request);
    return (status+ CRLF + buildAllowedHeader(methods)).getBytes();
  }

  private ArrayList<String> getAllowedMethods(Request request) {
    String route = request.getRoute();
    return directory.get(route);
 }

  private String buildAllowedHeader(ArrayList<String> methods) {
    StringBuilder allowed = new StringBuilder();
    allowed.append(this.allowHeader);
    for (String  restMethod : methods) {   
      allowed.append(restMethod+",");
    }
    return allowed.toString();
 }
}
