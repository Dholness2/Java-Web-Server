package com.javawebserver.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.responses.GetForm;
import com.javawebserver.app.responses.Get;
import com.javawebserver.app.Request;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;

public class GetFormTest {
  private ResponseBuilder response = new HttpResponseBuilder();
  private String formData = "My=Data";
  private String fileName ="/form";
  private  String fileType = "text/plain";
  private String directory = System.getProperty("user.dir");
  private boolean authenticationEnabled = false;
  private GetForm testGetForm = new GetForm(response,fileName,fileType,directory,authenticationEnabled,formData);

  private String buildResponse (String path, String contentType) throws IOException{
    String typeHeader = "Content-Type: ";
    String contentLength = "Content-Length: ";
    String fileLength = String.valueOf(getFile(path).length);
    this.response.addStatus("OK");
    this.response.addHeader(typeHeader, contentType);
    this.response.addHeader(contentLength,(fileLength));
    this.response.addBody(formData.getBytes());
    String response = new String (this.response.getResponse());
    this.response.clearBuilder();
    return response;
  }

  private byte [] getFile(String path) throws IOException {
    Path location = Paths.get(path);
    return Files.readAllBytes(location);
  }

  @Test
  public void hasFileNonExistantFileTest() {
    testGetForm.buildPath();
    assertFalse(this.testGetForm.hasFile());
  }

  @Test
  public void hasPresentFileTest() {
    Request request = new Request();
    this.testGetForm.handleRequest(request);
    assertTrue(testGetForm.hasFile());
  }

  @Test
  public void buildFormFileTest() {
    GetForm testGetForm = new GetForm(response,fileName,fileType,directory,authenticationEnabled,formData);
    testGetForm.buildPath();
    testGetForm.buildFormFile();
    assertTrue(testGetForm.hasFile());
  }

  @Test
  public void handleRequestNewFormFileTest() throws Exception {
    Get testGetForm = new GetForm(response,fileName,fileType,directory,authenticationEnabled,formData);
    byte [] response = testGetForm.handleRequest(new Request());
    String testResponse = new String(response);
    String expectedResponse = buildResponse((directory+fileName),fileType);
    assertEquals(expectedResponse,testResponse);
  }
}
