package com.javawebserver.app;
import com.javawebserver.app.serverBuilders.CobSpecServerBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class App {
  private static final int PORT_INDEX = 0;
  private static final int DIR_INDEX = 1;
  private static final String[] KEYS = {"-p","-d"};

  public static void main( String[] args) throws Exception {
    Map<String, String> Options = OptionsParser.parse(args,KEYS);
    String directory = Options.get(KEYS[DIR_INDEX]);
    int port = Integer.parseInt(Options.get(KEYS[PORT_INDEX]));

    CobSpecServerBuilder cobSpecBuilder = new CobSpecServerBuilder(directory);
    Server appServer = cobSpecBuilder.getServer(port, directory);
    appServer.run();
  }
}
