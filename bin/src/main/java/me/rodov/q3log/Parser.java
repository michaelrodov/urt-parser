package me.rodov.q3log;

import com.google.gson.Gson;
import org.apache.commons.cli.*;

import java.io.*;

/**
 * Created by Carlos on 06/03/2016.
 */
public class Parser {
    public static void main(String[] args) {
        Games games;
        FileWriter fileWriter;
        FileReader fileReader;


        Options options = new Options();
        options.addOption("l",true, "log file location");
        options.addOption("o",true, "output json file location");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse( options, args);
            String logPath = cmd.getOptionValue("l");

            try {
                fileReader = new FileReader(logPath);
                fileWriter = new FileWriter(cmd.getOptionValue("o"));

                games = Helper.readLog(new BufferedReader(fileReader));
                Gson gson = new Gson();
                String json = gson.toJson(games);
                fileWriter.write(json);

                fileReader.close();
                fileWriter.close();

            } catch (FileNotFoundException e) {
                System.err.println("Log file not found in "+logPath+"\n\n" + e.getMessage());
            } catch (IOException e) {
                System.err.println("IO failed: \n\n" + e.getMessage());
            } catch (Exception e) {
                System.err.println("Parsing log failed with: \n\n" + e.getMessage());
            }


        } catch (ParseException e) {
            System.err.println("Parsing failed: " + e.getMessage());
        }

        System.out.println("Parsing finished successfully.");
    }
}
