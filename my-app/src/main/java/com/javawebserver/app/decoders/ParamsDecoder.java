package com.javawebserver.app.decoders;

import java.util.HashMap;
import java.util.Map;

public class ParamsDecoder {
  private static String decoded;

  public static String decodeParams(HashMap<String, String> paramsKey, String encoded) {
    decoded = encoded;
    paramsKey.forEach((param,character)->{
      replaceExistingParam(param, character, encoded);
    });
    return decoded;
  }

  private static void replaceExistingParam(String param, String character, String encoded) {
    if (encoded.contains(param)) {
      decoded = decoded.replaceAll(param,character);
    }
  }
}
