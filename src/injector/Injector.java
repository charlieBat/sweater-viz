package injector;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Andrew on 2019-11-17.
 */
public class Injector {

    String mainChecker = "public static void main(String[] args)";
    String mainInjectable = "Some main"; //Should contain code to create monitoring thread
    String endInjectable = "Some end"; //Should contain monitoring thread code
    String in = null;
    String out = "";
    FileOutputStream output = null;

    public Injector()
    {

    }

    public void injectCode(String inputFile, String outputFile) throws IOException
    {
        try
        {
            //Get input file
            in = new String(Files.readAllBytes(Paths.get(inputFile)), StandardCharsets.UTF_8);
            String [] tokens=in.split("\n");
            StringBuilder str = new StringBuilder("");

            //Rewrite each line of code.
            for (int i = 0; i < tokens.length; i++) {
                str.append(tokens[i] + "\n");
                //Locate main() method.
                if (tokens[i].contains(mainChecker))
                {
                    //Inject code for main()
                    str.append(mainInjectable + "\n");
                }
            }
            //Inject code at end.
            str.append(endInjectable);

            //Write to output file.
            out = str.toString();
            output = new FileOutputStream(outputFile);
            byte[] strToBytes = out.getBytes();
            output.write(strToBytes);
        }
        finally
        {
            if (output != null)
            {
                output.close();
            }
        }
    }
}
