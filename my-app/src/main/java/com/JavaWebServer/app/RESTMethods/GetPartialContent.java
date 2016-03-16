package com.JavaWebServer.app;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;

public class GetPartialContent implements RestMethod {
  private String status;
  private String fileName;
  private String directory;
  private String contentType;
  private int startIndex;
  private int endIndex;
  private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private static final String STATUSERROR = "HTTP/1.1 500 Internal Server Error";
  private static final String CRLF = System.getProperty("line.separator");
  private static final String TYPEHEADER = "Content-Type: ";
  private static final String LENGTHHEADER = "Content-Length:";
  //private static final String RangeHeader = "Content-Range: ;
  private static final String RANGE = "Range:";

  public GetPartialContent (String status, String fileName, String contentType, String directory) {
    this.status = status;
    this.fileName = fileName;
    this.contentType = contentType;
    this.directory = directory;
  }

  public byte[] handleRequest(Request request) {
    try {
      Path path = getPath();
      return getResponse(path,request);
    }catch (IOException e) {
      System.out.println("path not found"+ e);
    }
    return STATUSERROR.getBytes();
  }

  private byte [] getResponse(Path path, Request request) {
    try {
      byte[] fileBytes = Files.readAllBytes(path);
      setRange(request.getHeaders(), (fileBytes.length - 1));
      return fileResponse(fileBytes);
    }catch (IOException e) {
      System.out.println("path not found"+ e);
    }
    return STATUSERROR.getBytes();
  }

  private byte [] fileResponse(byte [] file) {
    byte [] partial = getPartial(file);
    try{
      this.outputStream.reset();
      this.outputStream.write(buildHeader(partial.length));
      this.outputStream.write(partial);
      return outputStream.toByteArray();
    } catch (IOException e) {
      System.out.println("could not write file" + e);
      return STATUSERROR.getBytes();
    }
  }


  private  byte []  getPartial(byte [] file) {
    System.out.println(this.startIndex +"range"+ this.endIndex);
    return Arrays.copyOfRange(file, this.startIndex, (this.endIndex + 1));
  }

  private byte [] buildHeader (int fileLength) {
    return (status +CRLF+TYPEHEADER
            + contentType+CRLF+LENGTHHEADER
            + fileLength +CRLF+CRLF).getBytes();
  }

  private void  setRange(String header, int fileLength){
    String range = header.substring((header.indexOf("=") + 1), header.indexOf(System.getProperty("line.separator")));
    int byteIndex = range.indexOf("=");
    int seperatorIndex = range.indexOf("-");
    if(seperatorIndex  == 0){
      this.startIndex = (fileLength - (Integer.parseInt(range.substring(1)) - 1));
      this.endIndex = fileLength;
    } else if((seperatorIndex + 1) > (range.length() - 1)) {
      this.startIndex = Integer.parseInt(range.substring(0, seperatorIndex));
      this.endIndex = fileLength;
    }else {
      this.startIndex = Integer.parseInt(range.substring(0, seperatorIndex));
      this.endIndex = Integer.parseInt(range.substring((seperatorIndex + 1)));
    }
  }

  private Path getPath() throws IOException {
    String location = this.directory + this.fileName;
    Path path = Paths.get(location);
    return path;
  }
}
