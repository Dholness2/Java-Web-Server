package com.JavaWebServer.app.responses;

import com.JavaWebServer.app.responses.RestMethod;
import com.JavaWebServer.app.StatusCodes;
import com.JavaWebServer.app.Request;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.io.IOException;
import java.io.ByteArrayOutputStream;

import java.util.Arrays;

public class GetPartialContent implements RestMethod {
  private StatusCodes status;
  private String fileName;
  private String directory;
  private String contentType;
  private int startIndex;
  private int endIndex;
  private static final String CRLF = System.getProperty("line.separator");
  private static final String TYPEHEADER = "Content-Type: ";
  private static final String LENGTHHEADER = "Content-Length:";
  private static final String RANGEHEADER = "Content-Range: bytes */";

  public GetPartialContent (StatusCodes status, String fileName, String contentType, String directory) {
    this.status = status;
    this.fileName = fileName;
    this.contentType = contentType;
    this.directory = directory;
  }

  public byte[] handleRequest(Request request) {
    try {
      byte[] fileBytes = Files.readAllBytes(getPath());
      return getResponse(request,fileBytes);
    }catch (IOException e) {
      e.printStackTrace();
    }
    return status.STATUSERROR.getBytes();
  }

  private byte [] getResponse (Request request, byte [] fileBytes) {
    if (hasRangeHeaders(request)){
      return getPartialResponse(fileBytes, request);
    }
    return getResponseOutput(fileBytes);
  }

  private boolean hasRangeHeaders(Request request){
    String expectedHeader = "Range:";
    return ((request.getHeaders() != null) && (request.getHeaders().contains(expectedHeader)));
  }

  private Path getPath() {
    return Paths.get(this.directory + this.fileName);
  }

  private byte [] getPartialResponse (byte [] fileBytes, Request request ) {
    setRange(request.getHeaders(), (fileBytes.length - 1));
    if(validRange(fileBytes)){
      return getResponseOutput(getPartial(fileBytes));
    }
    return (status.RANGE_NOT_SATSIFIABLE+CRLF + RANGEHEADER +fileBytes.length).getBytes();
  }

  private  byte []  getPartial(byte [] file) {
    return Arrays.copyOfRange(file, this.startIndex, (this.endIndex + 1));
  }

  private boolean validRange (byte [] file) {
    return ((this.startIndex >= 0) && (this.endIndex <= (file.length)));
  }

  private byte [] getResponseOutput(byte [] file) {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try{
      output.write(buildHeader(file.length));
      output.write(file);
      return output.toByteArray();
    } catch (IOException e) {
      new Exception("Could not write to file").printStackTrace();
      e.printStackTrace();
    }
    return status.STATUSERROR.getBytes();
  }

  private byte [] buildHeader (int fileLength) {
    return (status.PARTIAL+CRLF+TYPEHEADER
        + contentType+CRLF+LENGTHHEADER
        + fileLength +CRLF+CRLF).getBytes();
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
