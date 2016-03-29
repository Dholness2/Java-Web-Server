package com.JavaWebServer.app.decoders;

import java.util.Base64;

public class Base64Decoder{

 public static String decode (String encoded) {
    return new String (Base64.getDecoder().decode(encoded));
 }
}
