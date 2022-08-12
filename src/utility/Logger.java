package utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {public static String fileName = "login_activity.txt";

    public static void createLogger (String log){
        try{


            FileWriter fWriter = new FileWriter(fileName, true);
            PrintWriter lFile = new PrintWriter(fWriter);
            lFile.println(log);
            lFile.close();
            lFile.println(log + " recorded successfully");


        } catch (IOException e) {
            System.out.println("Logger error: " + e.getMessage());
        }
    }
}
