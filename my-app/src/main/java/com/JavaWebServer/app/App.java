package com.JavaWebServer.app;

import java.util.Map;
import java.util.HashMap;

public class App {
  private static final int PORT_INDEX = 0;
  private static final String [] KEYS = {"-p","-d"};

  public static void main( String[] args) throws Exception {
    Map<String,String> parsedArguments = OptionsParser.parse(args, KEYS);
    int port = Integer.parseInt(parsedArguments.get(KEYS[PORT_INDEX]));

    ServerSocket serverSocket = new ServerSocket(port);
    Server app = new Server(port,serverSocket);
    new Thread(app).start();
  }
}
