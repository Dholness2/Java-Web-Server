package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.ExceptionLogger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.io.IOException;
import java.io.ByteArrayOutputStream;

import java.util.Arrays;

public class GetPartialContent implements Response {
  private ResponseBuilder responseBuilder;
  private String fileName;
  private String directory;
  private String contentType;
  private int startIndex;
  private int endIndex;

  private static final String CRLF = System.getProperty("line.separator");
  private static final String TYPE_HEADER = "Content-Type: ";
  private static final String LENGTH_HEADER = "Content-Length:";
  private static final String RANGE_HEADER = "Content-Range: bytes */";
  private static final String INTERNAL_ERROR_CODE = "500";
  private static final String RANGE_NOT_SATSIFIABLE = "416";
  private static final String PARTIAL_STATUS_CODE = "206";

  public GetPartialContent (ResponseBuilder responseBuilder, String fileName, String contentType, String directory) {
    this.responseBuilder = responseBuilder;
    this.fileName = fileName;
    this.contentType = contentType;
    this.directory = directory;
  }

  public byte[] handleRequest(Request request) {
    try {
      byte[] fileBytes = Files.readAllBytes(getPath());
      return getResponse(request,fileBytes);
    }catch (IOException e) {
      ExceptionLogger.logException("can't read file" + e);
    }
    return responseBuilder.getStatus(INTERNAL_ERROR_CODE);
  }

  private byte [] getResponse (Request request, byte [] fileBytes) {
    if (hasRangeHeaders(request)) {
      return getPartialResponse(fileBytes, request);
    }
    return basicResponse(fileBytes);
  }

  private boolean hasRangeHeaders(Request request) {
    String expectedHeader = "Range:";
    return ((request.getHeaders() != null) && (request.getHeaders().contains(expectedHeader)));
  }

  private Path getPath() {
    return Paths.get(this.directory + this.fileName);
  }

  private byte[] getPartialResponse (byte[] fileBytes, Request request) {
    setRange(request.getHeaders(), (fileBytes.length - 1));
    if (validRange(fileBytes)) {
      return validRangeResponse(getPartial(fileBytes));
    }
    return invalidRangeResponse(fileBytes);
  }

  private byte[] validRangeResponse(byte[] file) {
    ResponseBuilder currentResponse = this.responseBuilder.clone();
    currentResponse.addStatus(PARTIAL_STATUS_CODE);
    currentResponse.addHeader(TYPE_HEADER, this.contentType);
    currentResponse.addHeader(LENGTH_HEADER, Integer.toString(file.length));
    currentResponse.addBody(file);
    return currentResponse.getResponse();
  }

  private byte[] basicResponse(byte[] file) {
    ResponseBuilder currentResponse = this.responseBuilder.clone();
    currentResponse.addStatus("200");
    currentResponse.addHeader(TYPE_HEADER, this.contentType);
    currentResponse.addHeader(LENGTH_HEADER, Integer.toString(file.length));
    currentResponse.addBody(file);
    return currentResponse.getResponse();
  }

  private byte[] invalidRangeResponse(byte[] fileBytes) {
    ResponseBuilder currentResponse = this.responseBuilder.clone();
    currentResponse.addStatus(RANGE_NOT_SATSIFIABLE);
    currentResponse.addHeader(RANGE_HEADER, Integer.toString(fileBytes.length));
    return currentResponse.getResponse();
  }

  private byte[]  getPartial(byte[] file) {
    return Arrays.copyOfRange(file, this.startIndex, (this.endIndex + 1));
  }

  private boolean validRange (byte[] file) {
    return ((this.startIndex >= 0) && (this.endIndex <= (file.length)));
  }

  private void setRange(String header, int fileLength){
    String range = getRange(header);
    int separatorIndex = range.indexOf("-");
    if(lowerBoundRequest(separatorIndex)){
      this.startIndex = (fileLength - (Integer.parseInt(range.substring(1)) - 1));
      this.endIndex = fileLength;
    } else if(upperBoundRequest(separatorIndex, range)) {
      this.startIndex = Integer.parseInt(range.substring(0, separatorIndex));
      this.endIndex = fileLength;
    }else {
      this.startIndex = Integer.parseInt(range.substring(0, separatorIndex));
      this.endIndex = Integer.parseInt(range.substring((separatorIndex + 1)));
    }
  }

  private String getRange (String header){
    return header.substring((header.indexOf("=") + 1), header.indexOf(CRLF));
  }

  private boolean lowerBoundRequest (int separatorIndex) {
    return (separatorIndex == 0);
  }

  private boolean upperBoundRequest(int separatorIndex, String range){
    return ((separatorIndex + 1) > (range.length() - 1));
  }
}
