package com.JavaWebServer.app;
import java.util.Base64;

public class base64Decoder{

  public  base64Decoder () {}

  public String decode (String encoded) {
    return new String (Base64.getDecoder().decode(encoded));
  }
}
