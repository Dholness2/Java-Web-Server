package com.JavaWebServer.app;
import java.util.*;

public class OptionsParser {
  
  public static HashMap parse (String [] args, String [] keys) {
    HashMap results = new HashMap();
    
    int argsSize = args.length;
    int keysSize = keys.length;
    
    for(int x = 0; x < keysSize; x++) {
      for(int y = 0; y < argsSize; y++) {
        if (keys[x] == args[y] && !(results.containsKey(keys[x]))) {
           results.put(keys[x],args[(y + 1)]);
        }
      }
    }
    return results;
  }
}
