package com.JavaWebServer.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileEditor {
  private static ArrayList<String> lines = new ArrayList<String>();

  public static void edit(String path, String replace, String edit) {
    File currentFile = new File(path);
    FileReader reader;
    try {
      reader = new FileReader(currentFile);
      BufferedReader bufferedReader = new BufferedReader(reader);
      editLines(bufferedReader,replace,edit);
      reader.close();
      bufferedReader.close();
    } catch (Exception e) {
      System.out.println("cant read file" +e);
    }
    createNewFile(currentFile);
  }

  private static void editLines(BufferedReader reader,String replace, String edit){
    String line;
    try{
      while ((line = reader.readLine()) != null){
        if (line.contains(replace)){
          line = line.replace(replace, edit);
        }
        lines.add(line);
      }
    } catch (Exception e) {
      System.out.println("cant read line" +e);
    }
  }

  private static  void createNewFile(File oldFile ) {
    try{
      FileWriter writer = new FileWriter(oldFile);
      BufferedWriter bufferWriter = new BufferedWriter(writer);
      for(String line : lines){
        bufferWriter.write(line);
        bufferWriter.flush();
        bufferWriter.close();
      }
    } catch (Exception e) {
      System.out.println("cant write line" +e);
    }
  }
}
