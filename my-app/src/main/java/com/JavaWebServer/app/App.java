package com.JavaWebServer.app;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class App {
  private static final int PORT_INDEX = 0;
  private static final String [] KEYS = {"-p","-d"};
  private static int port;
  private static String directory = "/Users/don/desktop/cob_spec/public/";

  public static HashMap routeDirectory(){
    HashMap<String, ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
    routes.put("/file1",routeMethods(new String [] {"GET", "POST"}));
    routes.put("/",routeMethods(new String [] {"GET","PUT"}));
    routes.put("/text-file.txt",routeMethods(new String [] {"GET","PUT"}));
    routes.put("/form",routeMethods(new String [] {"POST","PUT"}));
    routes.put("/method_options",routeMethods(new String [] {"GET","HEAD","POST","OPTIONS","PUT"}));
    routes.put("/redirect",routeMethods(new String [] {"GET"}));
    routes.put("/image.jpeg",routeMethods(new String[] {"GET"}));
    routes.put("/image.gif",routeMethods(new String[] {"GET"}));
    routes.put("/image.png",routeMethods(new String[] {"GET"}));
    routes.put("/file1",routeMethods(new String[] {"GET"}));
    routes.put("/file2",routeMethods(new String[] {"GET"}));
    routes.put("/parameters?",routeMethods(new String[] {"GET"}));
    return routes;
  }

  public static Map <String, String>  paramatersEncodingKeyMap() {
    Map<String, String> encode = new HashMap<String, String>();
    encode.put("%3C","<");
    encode.put("%3E",">");
    encode.put("%3D","=");
    encode.put("%2C",",");
    encode.put("%21","!" );
    encode.put("%2B","+");
    encode.put("%2D","-");
    encode.put("%20"," ");
    encode.put("%22", "\"");
    encode.put("%3B",";");
    encode.put("%2A","*");
    encode.put("%26","&");
    encode.put("%40","@");
    encode.put("%23","#");
    encode.put("%24","\\$");
    encode.put("%5B","[");
    encode.put("%5D","]");
    encode.put("%3A",":");
    encode.put("%3F","?");
    return encode;
  }

  public static HashMap getRoutes (StatusCodes status,String path) {
    HashMap<String, RestMethod> routes = new HashMap<String,RestMethod>();
    routes.put("POST /file1",new Post(status.OK));
    routes.put("GET /", new GetDirectory(status,path));
    routes.put("PUT /", new Put(status.OK));
    routes.put("GET /image.jpeg", new Get(status.OK,"image.jpeg","image/jpeg", directory));
    routes.put("GET /image.gif", new Get(status.OK,"image.gif","image/gif",directory));
    routes.put("GET /image.png", new Get(status.OK,"image.png","image/png",directory));
    routes.put("GET /text-file.txt", new Get(status.OK, "text-file.txt","text/plain",directory));
    routes.put("Put /text-file.txt", new Put(status.OK));
    routes.put("POST /form", new Post(status.OK));
    routes.put("PUT /form", new Put(status.OK));
    routes.put("OPTIONS /method_options", new Options(status.OK));
    routes.put("GET /method_options", new Get(status.OK));
    routes.put("POST /method_options", new Post(status.OK));
    routes.put("HEAD /method_options", new Head(status.OK));
    routes.put("PUT /method_options", new Put(status.OK));
    routes.put("GET /redirect",new Get((status.FOUND+"\n\r"+ "Location: http://localhost:5000/")));
    routes.put("GET /file1",new Get(status.OK,"file1","text/plain", directory));
    routes.put("GET /file2",new Get(status.OK,"file2","text/plain", directory));
    routes.put("GET /parameters?", new Params(status.OK,"parameters?",paramatersEncodingKeyMap()));
    return routes;
  }

  private static ArrayList<String> routeMethods(String [] methods) {
   return new ArrayList<String>(Arrays.asList(methods));
  }

  private static String rootParent(String path) {
    int indexOfLastFolder = path.lastIndexOf("/",((path.length())-2));
    return ((path.substring(0 ,indexOfLastFolder)) + ("/"));
  }

  public static void main( String[] args) throws Exception {
    port = Integer.parseInt(OptionsParser.parse(args,KEYS).get(KEYS[PORT_INDEX]));
    StatusCodes httpStatuses = new StatusCodes ();
    HashMap <String, RestMethod> routes = getRoutes(httpStatuses,directory);
    Responder responder = new Responder(routeDirectory(), routes);
    ServerSocket serverSocket = new ServerSocket(port);
    Server app = new Server(port,serverSocket,responder);
    new Thread(app).start();
  }
}
