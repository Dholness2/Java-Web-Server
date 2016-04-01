package com.JavaWebServer.app;

import com.JavaWebServer.app.decoders.ParamsDecoder;

import java.util.HashMap;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ParamsDecoderTest {
  private HashMap<String, String> paramsKey = getParamsKey();

  @Test
  public void canDecodeParamsBasedOnParamsKey() {
    String[] expectedDecodings = new String[] {
                                   "<", ">", "=",
                                   ",", "!", ";",
                                   "+", "-", "*",
                                   "&", "@", "#",
                                   "$", " "
                                   };
    String[] encoded = new String[] {
                         "%3C", "%3E", "%3D",
                         "%2C", "%21", "%3B",
                         "%2B", "%2D", "%2A",
                         "%26", "%40", "%23",
                         "%24", "%20"
                         };
    for(int index = 0; index < encoded.length; index++){
      assertEquals(expectedDecodings[index], ParamsDecoder.decodeParams(paramsKey, encoded[index]));
    }
  }

  @Test
  public void canDecodeFullyEncodedString() {
    String encoded = "-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24";
    String expected = "-, *, &, @, #, $";
    assertEquals(expected, ParamsDecoder.decodeParams(paramsKey, encoded));
  }

  @Test
  public void canDecodeMixedString() {
    String encoded = "-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24";
    String expected = "-, *, &, @, #, $";
    assertEquals(expected, ParamsDecoder.decodeParams(paramsKey, encoded));
  }

  private static HashMap <String, String>  getParamsKey() {
    HashMap<String, String> encode = new HashMap<String, String>();
    encode.put("%3C","<");
    encode.put("%3E",">");
    encode.put("%3D","=");
    encode.put("%2C",",");
    encode.put("%21","!" );
    encode.put("%2B","+");
    encode.put("%2D","-");
    encode.put("%20"," ");
    encode.put("%22", "\"");
    encode.put("%3B",";");
    encode.put("%2A","*");
    encode.put("%26","&");
    encode.put("%40","@");
    encode.put("%23","#");
    encode.put("%24","\\$");
    encode.put("%5B","[");
    encode.put("%5D","]");
    encode.put("%3A",":");
    encode.put("%3F","?");
    return encode;
  }
}
