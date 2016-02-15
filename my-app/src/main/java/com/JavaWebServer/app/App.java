package com.JavaWebServer.app;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class App {
  private static final int PORT_INDEX = 0;
  private static final String [] KEYS = {"-p","-d"};
  private static int port;

  public static HashMap buildRoutes(){
    HashMap<String, ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
    routes.put("/file1",routeMethods(new String [] {"GET", "POST"}));
    routes.put("/",routeMethods(new String [] {"GET","PUT"}));
    routes.put("/text-file.txt",routeMethods(new String [] {"GET","PUT"}));
    routes.put("/form",routeMethods(new String [] {"POST","PUT"}));
    routes.put("/method_options",routeMethods(new String [] {"GET","HEAD","POST","OPTIONS","PUT"}));
    routes.put("/redirect",routeMethods(new String [] {"GET"}));
    return routes;
  }

  public static HashMap getRoutes (StatusCodes status) {
    HashMap<String, RestMethod> routes = new HashMap<String,RestMethod>();
    routes.put("GET /file1",new Get(status.OK));
    routes.put("POST /file1",new Post(status.OK));
    routes.put("GET /", new Get(status.OK));
    routes.put("PUT /", new Put(status.OK));
    routes.put("GET /text-file.txt", new Get(status.OK));
    routes.put("Put /text-file.txt", new Put(status.OK));
    routes.put("POST /form", new Post(status.OK));
    routes.put("PUT /form", new Put(status.OK));
    routes.put("OPTIONS /method_options", new Options(status.OK));
    routes.put("GET /method_options", new Get(status.OK));
    routes.put("POST /method_options", new Post(status.OK));
    routes.put("HEAD /method_options", new Head(status.OK));
    routes.put("PUT /method_options", new Put(status.OK));
    routes.put("GET /redirect",new Get((status.FOUND+"\n\r"+ "Location: http://localhost:5000/")));
    return routes;
  }

  private static ArrayList<String> routeMethods(String [] methods) {
   return new ArrayList<String>(Arrays.asList(methods));
  }

  public static void main( String[] args) throws Exception {
    StatusCodes httpStatuses = new StatusCodes ();
    HashMap <String, RestMethod> routes = getRoutes(httpStatuses);
    port = Integer.parseInt(OptionsParser.parse(args,KEYS).get(KEYS[PORT_INDEX]));

    Responder responder = new Responder(buildRoutes(), routes);
    ServerSocket serverSocket = new ServerSocket(port);
    Server app = new Server(port,serverSocket,responder);
    new Thread(app).start();
  }
}
