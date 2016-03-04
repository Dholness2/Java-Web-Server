package com.JavaWebServer.app;

import java.util.HashMap;
import java.util.Map;

public class GetDirectory implements RestMethod {
  private final String TYPE="text/html";
  private final String CRLF = "\r\n";

  private Map directoryLinks;
  private StatusCodes codes;
  private String url;

  public GetDirectory(StatusCodes codes, Map<String, String> dirLinks,String url) {
    this.directoryLinks = dirLinks;
    this.codes = codes;
    this.url = url;
  }

  public byte []  handleRequest(Request request) {
    String body = buildbody();
    String header = buildHeader(body,TYPE);
    return ((header+CRLF+CRLF+body).getBytes());
  }

  private String buildbody() {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("<!DOCTYPE html><html><body>");
    this.directoryLinks.forEach((link,title)->{
     strBuilder.append("<a href=\""+this.url+link+"\">"+title+"</a><br>"+CRLF);
    });
    return ((strBuilder.toString().trim()) + "</body></html>");
  }

  private String buildHeader (String body, String type) {
    String typeHeader = "Content-Type: ";
    String contentLength = "Content-Length:";
    int length = body.getBytes().length;
    return (codes.OK +CRLF+typeHeader+type+CRLF+contentLength+length);
  }
}
