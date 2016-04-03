package com.javawebserver.app;

import com.javawebserver.app.decoders.Base64Decoder;

import java.io.File;
import java.nio.charset.Charset;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.ArrayList;

public class Authenticator {
  private static String credentialsPath = System.getProperty("user.dir")+"/auth.txt";
  private static final String CRLF = System.getProperty("line.separator");
  private static final String credentialHeader = "Authorization: Basic ";

  public static boolean canAuthenticate (Request request) {
    if (hasValidHeader(request)){
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
      new Exception("File not found:").printStackTrace();
      e.printStackTrace();
    }
    return credentials;
  }

  private static boolean hasValidHeader(Request request) {
    return ((request.getHeaders() != null) && (request.getHeaders().contains(credentialHeader)));
  }

  private static String getCredential(Request request){
    String header = request.getHeaders().split(credentialHeader)[1];
    String credentials  = header.substring(0, header.indexOf(CRLF));
    return credentials;
  }

  private static String decodeCredential(String credential) {
    return  Base64Decoder.decode(credential);
  }
}
