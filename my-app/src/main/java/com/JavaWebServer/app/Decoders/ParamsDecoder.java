package com.JavaWebServer.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ParamsDecoder {
  private static String decoded;

  public static String decode( String encoded) {
    decoded = preFormatResponse(encoded);
    Map <String, String> encodingKey = getKeyMap();
    encodingKey.forEach((k,v)->{
      if (encoded.contains(k)){
        decoded = decoded.replaceAll(k,v);
      }
    });
    return decoded;
  }

  private static String preFormatResponse( String endcoded){
    return endcoded.replaceAll("=", " = ");
  }

  private static Map <String, String>  getKeyMap() {
    Map<String, String> encode = new HashMap<String, String>();
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
