package com.javawebserver.app.responses;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Get;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.Request;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class GetTest {
  private ResponseBuilder testResponse = new HttpResponseBuilder();
  private String directory = System.getProperty("user.dir")+"/public/";
  private String CRLF = System.getProperty("line.separator");
  private boolean protectedRoute = false;

  private String buildHeader (String path, String contentType) throws IOException{
    String typeHeader = "Content-Type: ";
    String contentLength = "Content-Length: ";
    String okStatusCode = "200";
    String fileLength = String.valueOf(getFile(path).length);
    this.testResponse.addStatus(okStatusCode);
    this.testResponse.addHeader(typeHeader, contentType);
    this.testResponse.addHeader(contentLength, (fileLength));
    String header = new String (this.testResponse.getResponse());
    this.testResponse.clearBuilder();
    return header.trim();
  }

  private byte [] getFile(String path) throws IOException {
    Path location = Paths.get(path);
    return Files.readAllBytes(location);
  }

  @Test
  public void handleSimepleRequestTest() {
    String simpleResponse = "HTTP/1.1 200 OK";
    Get testGet = new Get(this.testResponse);
    byte [] response = testGet.handleRequest(new Request());
    assertEquals(simpleResponse, new String(response));
  }

  @Test
  public void unauthorizedTest() {
    String type = "text/plain";
    String fileName = "logs";
    boolean protectedRoute = true;
    Get testGet = new Get(this.testResponse, fileName, type, directory, true);
    String response = (new String(testGet.handleRequest(new Request())));
    String expectedResponse = ("HTTP/1.1 401 Unauthorized" + CRLF + "WWW-Authenticate: Basic realm=logs" + CRLF);
    assertEquals(expectedResponse,response);
  }

  @Test
  public void handleRequestContentImageJpegTest() throws IOException {
    String fileName = "image.jpeg";
    String type = "image/jpeg";
    String path = directory + fileName;
    Get testGet = new Get(this.testResponse, fileName, type, directory, protectedRoute);
    byte [] testResponse = testGet.handleRequest(new Request());
    String expectedResponse = buildHeader(path, type);
    String response =((new String(testResponse)).split((CRLF + CRLF))[0]);
    assertEquals(expectedResponse, response);
  }

  @Test
  public void handleRequestContentImagePngTest() throws IOException {
    String fileName = "image.png";
    String type = "image/png";
    String path = directory + fileName;
    Get testGet = new Get(this.testResponse, fileName, type, directory, protectedRoute);
    byte [] testResponse = testGet.handleRequest(new Request());
    String expectedResponse = buildHeader(path, type);
    String response =((new String(testResponse)).split((CRLF + CRLF))[0]);
    assertEquals(expectedResponse, response);
  }

  @Test
  public void handleRequestContentImageGifTest() throws IOException {
    String fileName = "image.gif";
    String type = "image/gif";
    String path = directory + fileName;
    Get testGet = new Get(this.testResponse, fileName, type, directory, protectedRoute);
    byte [] testResponse = testGet.handleRequest(new Request());
    String expectedResponse = buildHeader(path,type);
    String response =((new String(testResponse)).split((CRLF + CRLF))[0]);
    assertEquals(expectedResponse, response);
  }

  @Test
  public void handleRequestContentfileTest() throws IOException {
    String fileName = "file1";
    String type = "txt/plain";
    String path = directory + fileName;
    Get testGet = new Get(this.testResponse, fileName, type, directory, protectedRoute);
    byte [] testResponse = testGet.handleRequest(new Request());
    String expectedResponse = buildHeader(path, type);
    String response =((new String(testResponse)).split((CRLF + CRLF))[0]);
    assertEquals(expectedResponse, response);
  }

  @Test
  public void handleRequestMissingFileTest() {
    Get testGet = new Get(this.testResponse,"images.gif","image/gif", directory, protectedRoute);
    byte [] response = testGet.handleRequest(new Request());
    String testResponse = new String(response);
    String expectedResponse = "HTTP/1.1 500 Internal Server Error";
    assertEquals(expectedResponse, testResponse);
  }
}
