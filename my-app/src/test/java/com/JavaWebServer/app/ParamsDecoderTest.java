package com.JavaWebServer.app;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ParamsDecoderTest {

  @Test
  public void decodeTest () {
    String expected = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $,"
                      +" [, ]: \"is that all\"?";
    String encoded="variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20"
                  +"-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22"+
                   "is%20that%20all%22%3F";
    assertEquals(expected, ParamsDecoder.decode(encoded));
  }

  @Test
  public void decodeMultipleParamsTest () {
    String expected = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $,"
                      +" [, ]: \"is that all\"?&variable_2 = stuff";
    String varOne="variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20"
                 +"-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22"+
                   "is%20that%20all%22%3F";
    String varTwo= "variable_2=stuff";
    String encoded = varOne+ "&"+varTwo;
    assertEquals(expected, ParamsDecoder.decode(encoded));
  }
}
