package com.JavaWebServer.app;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class OptionsParserTest  {
 @Test
 public void parseArugmentsTest() {
   String [] keys = {"-p","-d"};
   String [] testArguements = {"-p","9090","-d","PUBLIC_DIR"};
   Map <String,String> expectedResults = new HashMap<String, String>();
   Map <String, String> parsedArguments = OptionsParser.parse(testArguements, keys);

   expectedResults.put("-p", "9090");
   expectedResults.put("-d","PUBLIC_DIR");
   assertEquals(parsedArguments.keySet(), expectedResults.keySet());
   System.out.println(parsedArguments.keySet());
   System.out.println(parsedArguments.values());
 }
}
