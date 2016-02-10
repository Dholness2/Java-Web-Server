package com.JavaWebServer.app;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class App {
  private static final int PORT_INDEX = 0;
  private static final String [] KEYS = {"-p","-d"};


  public static HashMap buildRoutes(){
    ArrayList<String> routeMethods = new ArrayList<String>(); 
    routeMethods.add("GET");
    routeMethods.add("Put");
    HashMap<String, ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
    routes.put("/",routeMethods);
    routes.put("/file1",routeMethods);
    routes .put("/text-file.txt",routeMethods);
   return routes;
  }

  public static void main( String[] args) throws Exception {

    Map<String,String> parsedArguments = OptionsParser.parse(args, KEYS);
    int port = Integer.parseInt(parsedArguments.get(KEYS[PORT_INDEX]));

    Responder responder = new Responder(buildRoutes());
    ServerSocket serverSocket = new ServerSocket(port);
    Server app = new Server(port,serverSocket,responder);
    new Thread(app).start();
  }
}
