package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class DeleteTest {
 private  Request testRequest = new Request();
 private StatusCodes codes = new StatusCodes();
 private FileEditorMock editorMock = new FileEditorMock();
 private String path = "/foobar";


  @Test
  public void HandleRequestTest() {
    Delete testDelete = new Delete(codes.OK,path,editorMock);
    String response = new String (testDelete.handleRequest(testRequest));
    assertEquals(true, editorMock.fileChanged());
    assertEquals(codes.OK, response);
  }

  public  class FileEditorMock extends FileEditor {
    private boolean hasEditedFile = false;

    public FileEditorMock () {
    }

    @Override
    public void edit(String path, String post) {
      this.hasEditedFile = true;
    }

    public boolean fileChanged() {
      return this.hasEditedFile;
    }
  }
}
