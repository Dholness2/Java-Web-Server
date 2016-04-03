package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.StatusCodes;
import com.javawebserver.app.Request;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.Arrays;

public class GetDirectory implements Response {
  private final String TYPE="text/html";
  private final String CRLF = "\r\n";

  private String directoryPath;
  private StatusCodes codes;
  private String url;

  public GetDirectory(StatusCodes codes, String directoryPath) {
    this.directoryPath = directoryPath;
    this.codes = codes;
  }

  public byte []  handleRequest(Request request) {
    String  body = buildbody((getDirectoryList(this.directoryPath)));
    String header = buildHeader(body,TYPE);
    return ((header+CRLF+CRLF+body).getBytes());
  }

  private String buildbody(String [] dirList) {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("<!DOCTYPE html><html><body>");
    Arrays.sort(dirList);
    for(String file: dirList) {
      if (file.startsWith(".")){

      }else {
        strBuilder.append("<a href=\"/"+ file +"\">"+file+"</a><br>"+CRLF);
      }
    }
    return ((strBuilder.toString().trim()) + "</body></html>");
  }

  private String buildHeader (String body, String type) {
    String typeHeader = "Content-Type: ";
    String contentLength = "Content-Length:";
    int length = body.getBytes().length;
    return (codes.OK +CRLF+typeHeader+type+CRLF+contentLength+length);
  }

  private String [] getDirectoryList (String path) {
    File directory = new File(path);
    return directory.list();
  }
}
