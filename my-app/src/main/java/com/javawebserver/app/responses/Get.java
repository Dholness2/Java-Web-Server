package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.Authenticator;
import com.javawebserver.app.helpers.ExceptionLogger;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.Arrays;

public class Get implements Response {
  protected ResponseBuilder responseBuilder;
  protected String fileName;
  protected String directory;
  protected String contentType;
  protected boolean enabledProtection;

  private static final String OK_STATUS_CODE = "200";
  private static final String UNAUTHORIZED_STATUS_CODE = "401";
  private static final String INTERNAL_ERROR_STATUS_CODE = "500";
  private static final String TYPE_HEADER = "Content-Type: ";
  private static final String LENGTH_HEADER = "Content-Length: ";
  private static final String AUTH_HEADER ="WWW-Authenticate: Basic realm=";

  public Get (ResponseBuilder responseBuilder, String fileName, String contentType, String directory, boolean enabledProtection) {
    this.responseBuilder = responseBuilder;
    this.fileName = fileName;
    this.contentType = contentType;
    this.directory = directory;
    this.enabledProtection = enabledProtection;
  }

  public Get (ResponseBuilder responseBuilder) {
    this.responseBuilder = responseBuilder;
  }

  public byte[] handleRequest(Request request) {
    ResponseBuilder currentResponse = this.responseBuilder.clone();
    return getRequiredResponse(request, currentResponse);
  }

  private byte[] getRequiredResponse(Request request, ResponseBuilder currentResponse) {
    if (! isSimpleResponse()) {
      if (enabledProtection) {
        return getProtectedResponse(request, currentResponse);
      }
      return getResponse(currentResponse);
    }
    return currentResponse.getStatus(OK_STATUS_CODE);
  }

  private boolean isSimpleResponse() {
    return (this.fileName == null);
  }

  private byte[] getProtectedResponse(Request request, ResponseBuilder currentResponse) {
    if (Authenticator.canAuthenticate(request)) {
      return getResponse(currentResponse);
    }
    return noAccessResponse(currentResponse);
  }

  private byte[] getResponse(ResponseBuilder currentResponse) {
    try {
      byte[] file = Files.readAllBytes(Paths.get(getLocation()));
      return fileResponse(file, currentResponse);
    }catch (IOException e) {
     ExceptionLogger.logException("can't read file" + e);
    }
    return failedFileReadResponse(currentResponse);
  }

  private byte[] fileResponse (byte[] file, ResponseBuilder currentResponse) {
    currentResponse.addStatus(OK_STATUS_CODE);
    currentResponse.addHeader(TYPE_HEADER, contentType);
    currentResponse.addHeader(LENGTH_HEADER,String.valueOf(file.length));
    currentResponse.addBody(file);
    return currentResponse.getResponse();
  }

  private byte[] noAccessResponse(ResponseBuilder currentResponse) {
    currentResponse.addStatus(UNAUTHORIZED_STATUS_CODE);
    currentResponse.addHeader(AUTH_HEADER, this.fileName);
    return currentResponse.getResponse();
  }

  private byte[] failedFileReadResponse(ResponseBuilder currentResponse) {
    return currentResponse.getStatus(INTERNAL_ERROR_STATUS_CODE);
  }

  private String getLocation() {
    return this.directory + this.fileName;
  }
}
