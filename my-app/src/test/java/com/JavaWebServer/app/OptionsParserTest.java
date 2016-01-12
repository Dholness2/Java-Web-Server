package com.JavaWebServer.app;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.*;
public class OptionsParserTest  {
  @Test 
 public void parseArugmentsTest() {
   String [] keys = {"-p","-d"};
   String [] testArguements = {"-p","9090","-d","PUBLIC_DIR"};
   HashMap <String,String> expectedResults = new HashMap<String, String>();
   expectedResults.put("-p", "9090");
   expectedResults.put("-d","PUBLIC_DIR"); 
   assertEquals(OptionsParser.parse(testArguements, keys), expectedResults);
 }
}
