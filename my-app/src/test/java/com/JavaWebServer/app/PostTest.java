package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
public class PostTest {

  @Test
  public void handleRequestTest() {
    Request testrequest = new Request();
    testrequest.setMessage("POST / htttp1.1");
    RestMethod testPost = new Post();
    assertEquals("HTTP/1.1 200 ok", testPost.handleRequest(testrequest));
  }

  @Test
  public void setNextMethodTest() {
    RestMethod testPost = new Post();
    RestMethod testGet = new GetMethod();
    testPost.setNextMethod(testGet);
  }
}
