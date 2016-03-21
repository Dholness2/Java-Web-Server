package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class GetPartialContentTest {
  private Request  testRequest = new Request();
  private StatusCodes codes = new StatusCodes();
  private String directory = System.getProperty("user.dir")+"/public";
  private String CRLF = System.getProperty("line.separator");

  private String buildHeader (String path, String contentType) throws IOException{
    String typeHeader = "Content-Type: ";
    String contentLength = "Content-Length:";
    String rangestart = "Range:";
    int fileLength = getFile(path).length;
    return (codes.OK +CRLF+typeHeader
        + contentType+CRLF+contentLength
        + fileLength);
  }

  private String getResponseBody(byte [] response) {
    return ((new String(response)).split((CRLF +CRLF))[1]);
  }

  private String getPartialContent(  byte []  file, int startIndex, int endIndex) {
   return  (new String (file)).substring(startIndex, endIndex);
  }

  private byte [] getFile(String file) throws IOException {
    Path location = Paths.get(directory+ file);
    return Files.readAllBytes(location);
  }

  @Test
  public void handleRequestContentfileTestDualRange() throws IOException {
     String fileName = "/partial_content.txt";
     String type = "txt/plain";
     testRequest.setMessage("GET /partial_content.txt HTTP/ 1.1");
     testRequest.setHeaders("Range: bytes=0-4"+CRLF+CRLF);
     RestMethod testGetPartial = new GetPartialContent(codes,fileName,type, directory);
     byte [] testResponse = testGetPartial.handleRequest(testRequest);
     String responseBody = getResponseBody(testResponse);
     String expectedResponse = getPartialContent(getFile(fileName),0,5);
     assertEquals(expectedResponse,responseBody);
  }

  @Test
  public void handleRequestContentfileTestLowerRange() throws IOException {
     String fileName = "/partial_content.txt";
     String type = "txt/plain";
     testRequest.setMessage("GET /partial_content.txt HTTP/ 1.1");
     testRequest.setHeaders("Range: bytes=-6"+CRLF+CRLF);
     RestMethod testGetPartial = new GetPartialContent(codes,fileName,type, directory);
     byte [] testResponse = testGetPartial.handleRequest(testRequest);
     String responseBody = getResponseBody(testResponse);
     String expectedResponse = getPartialContent(getFile(fileName),71,77);
     assertEquals(expectedResponse,responseBody);
  }

  @Test
  public void handleRequestPartialContentfileTestUpperRange() throws IOException {
     String fileName = "/partial_content.txt";
     String type = "txt/plain";
     testRequest.setMessage("GET /partial_content.txt HTTP/ 1.1");
     testRequest.setHeaders("Range: bytes=4-"+CRLF+CRLF);
     RestMethod testGetPartial = new GetPartialContent(codes,fileName,type, directory);
     byte [] testResponse = testGetPartial.handleRequest(testRequest);
     String responseBody = getResponseBody(testResponse);
     String expectedResponse = getPartialContent(getFile(fileName),4,77);
     assertEquals(expectedResponse,responseBody);
  }

  @Test
  public void handleRequestPartialContentfileTestOutOfRange() throws IOException {
     String fileName = "/partial_content.txt";
     String type = "txt/plain";
     testRequest.setMessage("GET /partial_content.txt HTTP/ 1.1");
     testRequest.setHeaders("Range: bytes=4-100"+CRLF+CRLF);
     RestMethod testGetPartial = new GetPartialContent(codes,fileName,type, directory);
     byte [] testResponse = testGetPartial.handleRequest(testRequest);
     String response = new String(testResponse);
     String expectedResponse = "HTTP/1.1 416 Range Not Satisfiable"+CRLF+"Content-Range: bytes */77";
     assertEquals(expectedResponse,response);
  }

  @Test
  public void handleRequestPartialContentfileTestInternalError() throws IOException {
     String fileName = "/foo_content.txt";
     String type = "txt/plain";
     testRequest.setMessage("GET /foo_content.txt HTTP/ 1.1");
     testRequest.setHeaders("Range: bytes=4-100"+CRLF+CRLF);
     RestMethod testGetPartial = new GetPartialContent(codes,fileName,type, directory);
     byte [] testResponse = testGetPartial.handleRequest(testRequest);
     String response = new String (testResponse);
     String expectedResponse = codes.STATUSERROR;
     assertEquals(expectedResponse,response);
  }
}
