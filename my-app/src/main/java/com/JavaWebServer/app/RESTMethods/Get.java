package com.JavaWebServer.app;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;

public class Get implements RestMethod {
  private StatusCodes status;
  private String fileName;
  private String directory;
  private String contentType;
  private String simpleResponse;
  private boolean protectedRoute;
  private static final String CRLF ="\r\n";
  private static final String authHeader ="WWW-Authenticate: Basic realm=";
  private static final String TYPEHEADER = "Content-Type: ";
  private static final String LENGTHHEADER = "Content-Length:";

  public Get (StatusCodes status, String fileName, String contentType, String directory, boolean protectedRoute) {
    this.status = status;
    this.fileName = fileName;
    this.contentType = contentType;
    this.directory = directory;
    this.protectedRoute = protectedRoute;
  }

  public Get (String simpleResponse){
    this.simpleResponse = simpleResponse;
  }

  public byte[] handleRequest(Request request) {
    if (this.fileName != null ) {
      if(protectedRoute){
        return getProtectedResponse(request);
      }
      return getResponse();
    }
    return this.simpleResponse.getBytes();
  }

  private byte [] getProtectedResponse(Request request) {
    if(Authenticator.canAuthenticate(request)) {
      return getResponse();
    }
    return noAccessResponse();
  }

  private byte [] getResponse() {
    try {
      byte[] fileBytes = Files.readAllBytes(Paths.get(getLocation()));
      return fileResponse(fileBytes);
    }catch (IOException e) {
   
    }
    return status.STATUSERROR.getBytes();
  }

  private byte [] fileResponse(byte [] file) {
    ByteArrayOutputStream output= new ByteArrayOutputStream();
    try{
      output.write(buildHeader(file.length));
      output.write(file);
      return output.toByteArray();
    } catch (IOException e) {
      new Exception("could not write to Strem").printStackTrace();
      e.printStackTrace();
    }
    return status.STATUSERROR.getBytes();
  }

  private byte [] buildHeader (int fileLength) {
    return (status.OK +CRLF+TYPEHEADER
        + contentType+CRLF+LENGTHHEADER
        + fileLength +CRLF+CRLF).getBytes();
  }

  private byte [] noAccessResponse() {
    return (this.status.UNAUTHORIZED + CRLF
        + authHeader+ this.fileName +CRLF).getBytes();
  }

  private String getLocation()  {
    return this.directory + this.fileName;
  }
}
