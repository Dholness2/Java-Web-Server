package com.JavaWebServer.app;

import java.io.File;
import java.nio.charset.Charset;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.ArrayList;

public class Authenticator {
  private static String credentialsPath = System.getProperty("user.dir")+"/auth.txt";
  private static String CRLF = System.getProperty("line.separator");
  private static base64Decoder decoder = new base64Decoder();
  private static final String credentialHeader = "Authorization: Basic ";

  public static boolean authenticate (Request request) {
    if (validHeader(request)){
      ArrayList<String> validCredentials = getValidCredentials();
      return validCredentials.contains(decodeCredential(getCredential(request)));
    }
    return false;
  }

  private static ArrayList<String> getValidCredentials (){
    ArrayList<String> credentials = new ArrayList();
    try{
      credentials = new ArrayList<String>(Files.readAllLines(Paths.get(credentialsPath),Charset.forName("utf-8")));
    }catch (IOException e) {
      System.out.println("File not found" +e);
    }
    return credentials;
  }

  private static boolean validHeader(Request request) {
    return ((request.getHeaders() != null) && (request.getHeaders().contains(credentialHeader)));
  }

  private static String getCredential(Request request){
    String header = request.getHeaders().split(credentialHeader)[1];
    String credentials  = header.substring(0, header.indexOf(CRLF));
    System.out.println(credentials);
    return credentials;
  }

  private static String decodeCredential(String credential) {
    return  decoder.decode(credential);
  }
}
