package com.JavaWebServer.app;

import java.util.Scanner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FileEditorTest {
 private String path = "/Users/don/desktop/cob_spec/public/form";
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

 @Test
 public void EditfileTest () throws IOException {
   String replace = "Data=foobar";
   String edit = "Data=helloworld";
   FileEditor.edit(path,replace,edit);
   currentFile = new File(path);
   assertEquals(true, containsEdit(edit));
 }

}
