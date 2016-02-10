package com.JavaWebServer.app;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class App {
  private static final int PORT_INDEX = 0;
  private static final String [] KEYS = {"-p","-d"};
  private static int port;
  private static RestMethod methodChain;

  public static HashMap buildRoutes(){
    HashMap<String, ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
    routes.put("/file1",routeMethods(new String [] {"GET", "POST"}));
    routes.put("/",routeMethods(new String [] {"GET","PUT"}));
    routes.put("/text-file.txt",routeMethods(new String [] {"GET","PUT"}));
    routes.put("/form",routeMethods(new String [] {"POST","PUT"}));
    routes.put("/method_options",routeMethods(new String [] {"GET","HEAD","POST","OPTIONS","PUT"}));
    return routes;
  }

  public static RestMethod  BuildMethodChain() {
    RestMethod putMethod = new Put();
    RestMethod getMethod = new GetMethod();
    RestMethod postMethod = new Post();
    RestMethod optionsMethod = new Options();
    getMethod.setNextMethod(putMethod);
    putMethod.setNextMethod(optionsMethod);
    optionsMethod.setNextMethod(postMethod);
    return getMethod;
  }

  private static ArrayList<String> routeMethods(String [] methods) {
   return new ArrayList<String>(Arrays.asList(methods));
  }

  public static void main( String[] args) throws Exception {
    port = Integer.parseInt(OptionsParser.parse(args,KEYS).get(KEYS[PORT_INDEX]));
    methodChain = BuildMethodChain();

    Responder responder = new Responder(buildRoutes(), methodChain);
    ServerSocket serverSocket = new ServerSocket(port);
    Server app = new Server(port,serverSocket,responder);
    new Thread(app).start();
  }
}
