package com.JavaWebServer.app;

import java.util.Map;
import java.util.LinkedHashMap;

public class OptionsParser {

  public static Map<String,String> parse (String [] args, String [] keys) {
    Map<String,String> results = new LinkedHashMap();
    int argsSize = args.length;
    int keysSize = keys.length;
    for(int x = 0; x < keysSize; x++) {
      for(int y = 0; y < argsSize; y++) {
        if (keys[x].equals(args[y])) {
          results.put(keys[x],args[(y + 1)]);
        }
      }
    }
    return results;
  }
}
