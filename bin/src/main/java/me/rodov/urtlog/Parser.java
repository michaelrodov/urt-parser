package me.rodov.urtlog;

import com.google.gson.Gson;
import org.apache.commons.cli.*;

import java.io.*;

/**
 * Created by Carlos on 06/03/2016.
 */

public class Parser {
    public static String resultString = "SUCCESS";

    public static void main(String[] args) {
        Games games;
        FileWriter fileWriter;
        FileReader fileReader;


        Options options = new Options();
        options.addOption("l", true, "log file location");
        options.addOption("o", true, "output json file location");
        options.addOption("exclude", true, "exclude these players from games summary." +
                "\nPlayers list need to be delimited by colon, i.e. pla1,pla3");
        options.addOption("lim", true, "Optional. Filter out games shorter then X minutes.");
        options.addOption("types", true, "Optional. Include only certain game types and filter out others.\nIf not specified all games will be included\n" +
                "0-Free for All\n" +
                "3-Team Deathmatch\n" +
                "4-Team Survivor\n" +
                "5-Follow the Leader\n" +
                "6-Capture & Hold\n" +
                "7-Capture The Flag\n" +
                "8-Bomb & Defuse\n" +
                "9-Jump Mode\n" +
                "10-Freeze Tag\n");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            String logPath = cmd.getOptionValue("l");
            String timelimit = "0";
            String includedGameTypes = "0345678910"; //TODO rename freeze tag game type 10
            String excludedPlayers = "";
            if (cmd.hasOption("lim")) {
                timelimit = cmd.getOptionValue("lim");
            }
            if (cmd.hasOption("types")) {
                includedGameTypes = cmd.getOptionValue("types");
            }
            if (cmd.hasOption("exclude")) {
                excludedPlayers = " "+cmd.getOptionValue("exclude").replace(","," ")+" ";
            }
            try {
                fileReader = new FileReader(logPath);
                fileWriter = new FileWriter(cmd.getOptionValue("o"));

                games = Helper.readLog(new BufferedReader(fileReader), timelimit, includedGameTypes, excludedPlayers);
                Gson gson = new Gson();
                String json = gson.toJson(games);
                fileWriter.write(json);

                fileReader.close();
                fileWriter.close();
                System.out.println("Parsing finished successfully.");

            } catch (FileNotFoundException e) {
                System.err.println("There was a problem reading log OR writing output.\n\n" + e.getMessage());
                resultString = "FAILED";
            } catch (IOException e) {
                System.err.println("IO failed: \n\n" + e.getMessage());
                resultString = "FAILED";
            } catch (Exception e) {
                System.err.println("Parsing log failed with: \n\n" + e.getMessage());
                resultString = "FAILED";
            }


        } catch (ParseException e) {
            System.err.println("Parsing failed: " + e.getMessage());
            resultString = "FAILED";
        }
        System.out.println("\n\n" + resultString);
    }
}
