package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.JavaWebServer.app.responses.Response;
import com.JavaWebServer.app.responses.GetForm;
import com.JavaWebServer.app.responses.Get;
import com.JavaWebServer.app.StatusCodes;
import com.JavaWebServer.app.Request;

public class GetFormTest {
  private String CRLF = System.getProperty("line.separator");
  private String directory = System.getProperty("user.dir");
  private String formData = "My=Data";
  private StatusCodes testCodes = new StatusCodes();


  @Test
  public void handleRequestNewFormFileTest() {
    Get testgetform = new GetForm(testCodes,"form","text/plain",directory,false,formData);
    byte [] response = testgetform.handleRequest(new Request());
    String testResponse = new String(response);
    String expectedResponse = "";
    assertEquals(expectedResponse,testResponse);
  }

  @Test
  public void fileExistTest() {
    GetForm testGetForm = new GetForm(testCodes,"/form","text/plain",directory,true,formData);
    assertFalse(testGetForm.hasFile());
  }

  @Test
  public void getNewFormResponse() {
    int bodyIndex = 1;
    int header = 0;
    GetForm testGetForm = new GetForm(testCodes,"/form","text/plain",directory,true,formData);
    String [] response = new String(testGetForm.getNewFormResponse()).split(CRLF+CRLF);
    String bodyResponse = response[bodyIndex];
    assertEquals(formData,response);
  }
 }
