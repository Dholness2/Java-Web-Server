package com.JavaWebServer.app;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.ByteArrayOutputStream;


public class Get implements RestMethod {
  private String response;

  public Get (String response) {
    this.response = response;
  }

  public byte[] handleRequest(Request request)  {
    System.out.println(request.getRoute());
    if (request.getRoute().equals("/image.jpeg") ) {
    System.out.println( "image flow");
    try {
      String location = System.getProperty("user.dir") + "/public"+ request.getRoute();

      System.out.println(location);
      Path path = Paths.get(location);
      byte[] image = Files.readAllBytes(path);
      System.out.println("image" + image.length);
      //"Content-Type: image/jpeg"+"\r\n"+"Content-Length:"+ image.length +"\r\n"+ 
      byte []  header = (response +"\r\n").getBytes();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
      outputStream.write( header );
      outputStream.write(image);
      byte c[] = outputStream.toByteArray( );
      return c;
   } catch( IOException e) {
     System.out.println("cant read request"+ e);
   }

  }
  System.out.println(this.response); 
  return this.response.getBytes();
  }
}
