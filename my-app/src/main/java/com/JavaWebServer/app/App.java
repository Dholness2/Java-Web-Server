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

  public static HashMap getRoutes () {
    HashMap<String, RestMethod> routes = new HashMap<String,RestMethod>();
    routes.put("GET /file1",new Get("HTTP/1.1 200 ok"));
    routes.put("POST /file1",new Post());
    routes.put("GET /", new Get("HTTP/1.1 200 ok"));
    routes.put("PUT /", new Put());
    routes.put("GET /text-file.txt", new Get("HTTP/1.1 200 ok"));
    routes.put("Put /text-file.txt", new Put());
    routes.put("POST /form", new Post());
    routes.put("PUT /form", new Put());
    routes.put("OPTIONS /method_options", new Options());
    routes.put("GET /method_options", new Get("HTTP/1.1 200 ok"));
    routes.put("POST /method_options", new Post());
    routes.put("HEAD /method_options", new Head());
    routes.put("PUT /method_options", new Put());
    routes.put("GET /redirect",new Get(("HTTP/1.1 302 FOUND"+"\n\r"+ "Location: http://localhost:5000/")));
    return routes;
  }

  private static ArrayList<String> routeMethods(String [] methods) {
   return new ArrayList<String>(Arrays.asList(methods));
  }

  public static void main( String[] args) throws Exception {
    HashMap <String, RestMethod> routes = getRoutes();
    port = Integer.parseInt(OptionsParser.parse(args,KEYS).get(KEYS[PORT_INDEX]));

    Responder responder = new Responder(buildRoutes(), routes);
    ServerSocket serverSocket = new ServerSocket(port);
    Server app = new Server(port,serverSocket,responder);
    new Thread(app).start();
  }
}
