package com.JavaWebServer.app;

import java.util.Scanner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.After;

public class FileEditorTest {
 private String path = System.getProperty("user.dir")+"/form";
 private File currentFile;

 private boolean containsEdit(String edit) throws IOException {
  Scanner testScan = new Scanner(this.currentFile);
  int lineNum = 0;
  while (testScan.hasNextLine()) {
    String line = testScan.nextLine();
    lineNum++;
    if(line.contains(edit)) {
       return true;
    }
  }
  return false;
 }

 @After
 public void undoChanges(){
   String edit = "";
   String replace = "data=helloworld";
   new FileEditor().edit(path,edit);
 }

 @Test
 public void EditfileTest () throws IOException {
   String edit = "data=helloworld";
   new FileEditor().edit(path,edit);
   currentFile = new File(path);
   assertEquals(true, containsEdit(edit));
 }

}
