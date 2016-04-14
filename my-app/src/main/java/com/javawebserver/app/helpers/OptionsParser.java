package com.javawebserver.app;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Scanner;

import java.io.File;

public class OptionsParser {
  private Scanner input;
  private String[] args;

  private static final int PORT_INDEX = 0;
  private static final int DIR_INDEX = 1;
  private static final String[] KEYS = {"-p","-d"};

  public OptionsParser (Scanner input, String[] args) {
    this.input = input;
    this.args = args;
  }

  public String getDirectory() {
    String directory;
    directory = parseArgs().get(KEYS[DIR_INDEX]);
    File dir = new File(directory);
    if (dir.exists()) {
      return  dir.getAbsolutePath();
    }
    return getCorrectFilePath();
  }

  private String getCorrectFilePath() {
    System.out.print("Path does not exist, enter correct path:");
    String path = input.nextLine();
    File dir = new File(path);
    if (dir.exists()) {
      return dir.getAbsolutePath();
    }
    return getCorrectFilePath();
  }

  public int getPort(){
    int port;
    try {
      port = Integer.parseInt(parseArgs().get(KEYS[PORT_INDEX]));
    } catch (NumberFormatException e) {
      port = getNewPort();

    }
    return port;
  }

  private Map<String,String> parseArgs() {
    Map<String,String> results = new LinkedHashMap();
    for(int x = 0; x < KEYS.length; x++) {
      for(int y = 0; y < this.args.length; y++) {
        if (KEYS[x].equals(args[y])) {
          results.put(KEYS[x],args[(y + 1)]);
        }
      }
    }
    return results;
  }

  private int getNewPort () {
    System.out.print("Invalid port number please enter new port:");
    if (input.hasNextInt()){
      return input.nextInt();
    }
    return getNewPort();
  }
}
