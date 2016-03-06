package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
public class PostTest {

  @Test
  public void handleRequestTest() {
    StatusCodes codes = new StatusCodes();
    RestMethod testPost = new Post(codes.OK);
    String response = new String (testPost.handleRequest());
    assertEquals(codes.OK, response);
  }
}
