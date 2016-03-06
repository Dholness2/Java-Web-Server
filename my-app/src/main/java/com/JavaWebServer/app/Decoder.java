package com.JavaWebServer.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Decoder {
  private static String response;

  public static String  decode(Map<String, String> encoding, String code) {
    response = code.replaceAll("=", " = ");
    encoding.forEach((k,v)->{
      if (code.contains(k)){
        response = response.replaceAll(k,v);
      }
    });
    return response;
  }
}
