package com.JavaWebServer.app.responses;

import com.JavaWebServer.app.responses.Response;
import com.JavaWebServer.app.responseBuilders.ResponseBuilder;
import com.JavaWebServer.app.Request;
import com.JavaWebServer.app.Authenticator;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.Arrays;

public class Get implements Response {
  protected ResponseBuilder response;
  protected String fileName;
  protected String directory;
  protected String contentType;
  protected String simpleResponse;
  protected boolean protectedRoute;

  private static final String TYPE_HEADER = "Content-Type: ";
  private static final String LENGTH_HEADER = "Content-Length: ";
  private static final String AUTH_HEADER ="WWW-Authenticate: Basic realm=";

  public Get (ResponseBuilder response, String fileName, String contentType, String directory, boolean enabledProtection) {
    this.response = response;
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
      if (protectedRoute){
        return getProtectedResponse(request);
      }
      return getResponse();
    }
    return this.simpleResponse.getBytes();
  }

  private byte [] getProtectedResponse(Request request) {
    if (Authenticator.canAuthenticate(request)) {
      return getResponse();
    }
    return noAccessResponse();
  }

  private byte [] getResponse() {
    try {
      byte[] file = Files.readAllBytes(Paths.get(getLocation()));
      return fileResponse(file);
    }catch (IOException e) {
     new Exception("could not readfile").printStackTrace();
      e.printStackTrace();
    }
    return failedFileReadResponse();
  }

  private byte [] fileResponse (byte [] file) {
    this.response.addStatus("OK");
    this.response.addHeader(TYPE_HEADER,contentType);
    this.response.addHeader(LENGTH_HEADER,String.valueOf(file.length));
    this.response.addBody(file);
    byte [] fileResponse = this.response.getResponse();
    this.response.clearBuilder();
    return fileResponse;
  }

  private byte [] noAccessResponse() {
    this.response.addStatus("UNAUTHORIZED");
    this.response.addHeader(AUTH_HEADER, this.fileName);
    byte[] noAccessResponse = this.response.getResponse();
    this.response.clearBuilder();
    return noAccessResponse;
  }

  private byte [] failedFileReadResponse() {
    this.response.addStatus("STATUSERROR");
    byte [] failedResponse = this.response.getResponse();
    this.response.clearBuilder();
    return failedResponse;
  }

  private String getLocation()  {
    return this.directory + this.fileName;
  }
}
