package com.JavaWebServer.app;

import com.JavaWebServer.app.responses.RestMethod;
import com.JavaWebServer.app.responses.Options;
import com.JavaWebServer.app.StatusCodes;
import com.JavaWebServer.app.Request;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class OptionsTest {
  private HashMap<String, ArrayList<String>> routeDirectory = new HashMap<String, ArrayList<String>>();
  private Request testRequest = new Request();

  private  ArrayList<String> routeMethods(String [] methods) {
    return new ArrayList<String>(Arrays.asList(methods));
  }

  @Test
  public void handleRequestTest() {
    testRequest.setMessage("OPTIONS /method_options HTTP/1.1");
    this.routeDirectory.put("/method_options",routeMethods(new String [] {"GET","HEAD","POST","OPTIONS","PUT"}));
    StatusCodes code = new StatusCodes();
    RestMethod testOptions = new Options(code.OK, routeDirectory);
    String response = new String (testOptions.handleRequest(testRequest));
    assertEquals(code.OK+ System.lineSeparator() +"Allow: GET,HEAD,POST,OPTIONS,PUT,", response);
  }
}
