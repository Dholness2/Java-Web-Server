package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.GetPartialContent;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.FileEditor;

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
  private ResponseBuilder testResponse = new HttpResponseBuilder();
  private String directory = System.getProperty("user.dir")+"/public";
  private String CRLF = System.getProperty("line.separator");

  private String buildHeader (String path, String contentType) throws IOException{
    String typeHeader = "Content-Type: ";
    String contentLength = "Content-Length:";
    int fileLength = getFile(path).length;
    return ("HTTP/1.1 200 OK" + CRLF + typeHeader
            + contentType+ CRLF + contentLength
            + fileLength);
  }

  private String getResponseBody(byte[] response) {
    return ((new String(response)).split((CRLF +CRLF))[1]);
  }

  private String getPartialContent(byte[] file, int startIndex, int endIndex) {
    return (new String (file)).substring(startIndex, endIndex);
  }

  private byte [] getFile(String file) throws IOException {
    Path location = Paths.get(directory+ file);
    return Files.readAllBytes(location);
  }

  @Test
  public void handleRequestContentfileTestiDualRange() throws IOException {
    String fileName = "/partial_content.txt";
    String type = "txt/plain";
    testRequest.setMessage("GET /partial_content.txt HTTP/ 1.1");
    testRequest.setHeaders("Content-Range: bytes=0-4"+CRLF+CRLF);
    Response testGetPartial = new GetPartialContent(testResponse, fileName, type, directory);
    byte[] testResponse = testGetPartial.handleRequest(testRequest);
    String responseBody = getResponseBody(testResponse);
    String expectedResponse = getPartialContent(getFile(fileName),0,5);
    assertEquals(expectedResponse,responseBody);
  }

  @Test
  public void handleRequestNoRangeHeaderTest() throws IOException {
    Request  simpleGetRequest = new Request();
    String fileName = "/partial_content.txt";
    String type = "txt/plain";
    simpleGetRequest.setMessage("GET /partial_content.txt HTTP/ 1.1");
    Response testGetPartial = new GetPartialContent(testResponse, fileName, type, directory);
    byte[] testResponse = testGetPartial.handleRequest(simpleGetRequest);
    String responseBody = getResponseBody(testResponse);
    String expectedResponse = new String(getFile(fileName));
    assertEquals(expectedResponse,responseBody);
  }

  @Test
  public void handleRequestContentfileTestLowerRange() throws IOException {
    String fileName = "/partial_content.txt";
    String type = "txt/plain";
    testRequest.setMessage("GET /partial_content.txt HTTP/ 1.1");
    testRequest.setHeaders("Content-Range: bytes=-6"+CRLF+CRLF);
    Response testGetPartial = new GetPartialContent(testResponse, fileName, type, directory);
    byte[] testResponse = testGetPartial.handleRequest(testRequest);
    String responseBody = getResponseBody(testResponse);
    String expectedResponse = getPartialContent(getFile(fileName),71,77);
    assertEquals(expectedResponse,responseBody);
  }

  @Test
  public void handleRequestPartialContentfileTestUpperRange() throws IOException {
    String fileName = "/partial_content.txt";
    String type = "txt/plain";
    testRequest.setMessage("GET /partial_content.txt HTTP/ 1.1");
    testRequest.setHeaders("Content-Range: bytes=4-"+CRLF+CRLF);
    Response testGetPartial = new GetPartialContent(testResponse, fileName, type, directory);
    byte[] testResponse = testGetPartial.handleRequest(testRequest);
    String responseBody = getResponseBody(testResponse);
    String expectedResponse = getPartialContent(getFile(fileName),4,77);
    assertEquals(expectedResponse,responseBody);
  }

  @Test
  public void handleRequestPartialContentfileTestOutOfRange() throws IOException {
    String fileName = "/partial_content.txt";
    String type = "txt/plain";
    testRequest.setMessage("GET /partial_content.txt HTTP/ 1.1");
    testRequest.setHeaders("Content-Range: bytes=4-100"+CRLF+CRLF);
    Response testGetPartial = new GetPartialContent(testResponse, fileName, type, directory);
    byte[] testResponse = testGetPartial.handleRequest(testRequest);
    String response = new String(testResponse);
    String expectedResponse = "HTTP/1.1 416 Range Not Satisfiable"+ CRLF +"Content-Range: bytes */77" + CRLF;
    assertEquals(expectedResponse,response);
  }

  @Test
  public void handleRequestPartialContentfileTestInternalError() throws IOException {
    String fileName = "/foo_content.txt";
    String type = "txt/plain";
    testRequest.setMessage("GET /foo_content.txt HTTP/ 1.1");
    testRequest.setHeaders("Content-Range: bytes=4-100"+CRLF+CRLF);
    Response testGetPartial = new GetPartialContent(testResponse, fileName, type, directory);
    byte[] testResponse = testGetPartial.handleRequest(testRequest);
    String response = new String (testResponse);
    String expectedResponse = "HTTP/1.1 500 Internal Server Error";
    assertEquals(expectedResponse, response);
  }
}
