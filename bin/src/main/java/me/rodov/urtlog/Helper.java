package me.rodov.urtlog;

import ru.lanwen.verbalregex.VerbalExpression;

import java.io.BufferedReader;
import java.lang.Exception;

/**
 * Created by rudov on 29/02/2016.
 */
public class Helper {
    //test
    //todo replace by enum
    public final static int GAME_START_LINE = 1;
    public final static int PLAYER_REGISTRATION = 10;
    public final static int GAME_END_LINE = 2;
    public final static int GAME_END_REASON_LINE = 3;
    public final static int SCORE_LINE = 4;
    public final static int KILL_LINE = 5;
    public final static int GAME_RESULT_LINE = 6;
    public final static int FLAG_CAPTURE = 7;
    public final static int FLAG_RETURN = 8;
    public final static int FLAG_STEAL = 9;


    private final static String MAP = "mapname";
    private final static String TYPE = "g_gametype";
    private final static String HOSTNAME = "sv_hostname";
    private final static String TIMELIMIT = "timelimit";
    public final static String bannedWeapons = "MOD_CHANGE_TEAM,UT_MOD_BLED";


    // 0:29 InitRound: \sv_allowdownlo...
    public static VerbalExpression gameStart = VerbalExpression.regex()
            .find("InitGame: ").capture().anything().endCapture().build();

    //0:00 ClientUserinfo: 0 \ip\16.60.214.141:27960\name\Kilaka\
    public static VerbalExpression playerRegistration = VerbalExpression.regex()
            .find("ClientUserinfo: ").capture().digit().oneOrMore().endCapture().anything()
            .then("\\name\\").capture().anythingBut("\\").endCapture().then("\\").build();


    //red:3  blue:0
    public static VerbalExpression gameResult = VerbalExpression.regex()
            .capture().find(" red:").anything().endCapture().build();

    //20:44 ShutdownGame:
    public static VerbalExpression gameEnd = VerbalExpression.regex()
            .startOfLine().then(" ").capture().anything().endCapture().then(" ShutdownGame:").build();

    //20:23 Exit: Timelimit hit.
    public static VerbalExpression gameEndReason = VerbalExpression.regex()
            .find("Exit: ").capture().anything().endCapture().build();

    //20:23 score: 73  ping: 6  client: 5 Seb
    public static VerbalExpression playerScore = VerbalExpression.regex()
            .find("score: ").capture().digit().oneOrMore().endCapture().anything().then("client: ")
            .digit().oneOrMore().space().capture().anything().endCapture().build();

    //19:44 Kill: 9 2 17: RODOV killed Seb by UT_MOD_UMP45
    //<world> is technical game persona, not needed in stats
    public static VerbalExpression playerKill = VerbalExpression.regex()
            .find("Kill: ").anything().then(": ").capture().anything().endCapture().then(" killed ")
            .capture().anything().endCapture().then(" by ")
            .capture().anything().endCapture().build();

    //19:44 Kill: 9 2 17: RODOV killed Seb by UT_MOD_UMP45
    public static VerbalExpression playerDetails = VerbalExpression.regex()
            .find("ClientUserinfo: ").anything().then(": ").capture().anything().endCapture().then(" killed ")
            .capture().anything().endCapture().then(" by ")
            .capture().anything().endCapture().build();

    //  3:05 FlagCaptureTime: 6: 25650
    public static VerbalExpression flagCapture = VerbalExpression.regex()
            .find("FlagCaptureTime: ").capture().digit().oneOrMore().endCapture().then(": ").build();

    //  9:08 Flag: 3 1: team_CTF_redflag
    public static VerbalExpression flagReturn = VerbalExpression.regex()
            .find("Flag: ").capture().digit().oneOrMore().endCapture().then(" ").anythingBut("0").then(":").build();

    //  2:02 Item: 1 team_CTF_redflag
    public static VerbalExpression flagSteal = VerbalExpression.regex()
            .find("Item: ").capture().digit().oneOrMore().endCapture().then(" team_CTF_").anything().then("flag").build();

    public static VerbalExpression initGetMap = VerbalExpression.regex().find("\\mapname\\").capture().anything().endCapture().then("\\sv_privateClients").build();
    public static VerbalExpression initGetHost = VerbalExpression.regex().find("\\sv_hostname\\").capture().anything().endCapture().then("\\").build();
    public static VerbalExpression initGetGameType = VerbalExpression.regex().find("\\g_gametype\\").capture().digit().oneOrMore().endCapture().then("\\").build();
    public static VerbalExpression initGetGameTimeLimit = VerbalExpression.regex().find("\\timelimit\\").capture().digit().oneOrMore().endCapture().then("\\").build();

    private static long toSeconds(String duration) {
        String[] durationParsed = duration.trim().split(":");
        long durationSec = 0;
        if (durationParsed.length > 0) {
            durationSec += Long.valueOf(durationParsed[0]) * 60;
        }
        if (durationParsed.length > 1) {
            durationSec += Long.valueOf(durationParsed[1]);
        }
        return durationSec;
    }


    public static Games readLog(BufferedReader log, String timelimit, String includedGameTypes, String excludedPlayers) throws Exception {
        String line = new String();
        Games games = new Games();
        Game currentGame = new Game("game"); //create a new Game. the processing is linear so we can do it like this

        int lineType;

        try {
            //loop throughout the log
            while ((line = log.readLine()) != null) {
                if (!line.equals("")) {
                    lineType = getLineType(line); //analyze game type //TODO improve missing some states and too complicated

                    if (lineType == Helper.GAME_START_LINE) {
                        currentGame = new Game(games.getGames().size() + "_");
                        currentGame.init(line);
                    } else if (lineType == Helper.GAME_END_LINE) {
                        currentGame.setGameLength(Helper.gameEnd.getText(line, 1));
                        if (toSeconds(currentGame.getGameLength()) > toSeconds(timelimit)
                                && includedGameTypes.contains(String.valueOf(currentGame.getGameTypeId()))) {
                                    games.add(currentGame); //add a new Game to games list
                        }
                    } else if (lineType == Helper.PLAYER_REGISTRATION) {
                        currentGame.registerPlayer(Integer.valueOf(Helper.playerRegistration.getText(line, 1)), Helper.playerRegistration.getText(line, 2));
                    } else if (lineType == Helper.GAME_END_REASON_LINE) {
                        currentGame.setGameEndReason(Helper.gameEndReason.getText(line, 1));
                    } else if (lineType == Helper.FLAG_CAPTURE) {
                        currentGame.setPlayer(currentGame.getPlayersRegistrationId(Integer.valueOf(Helper.flagCapture.getText(line, 1))),
                                Player.FLAG_CAPTURE, 1, null); //+1 kill to the killer
                    } else if (lineType == Helper.FLAG_RETURN) {
                        currentGame.setPlayer(currentGame.getPlayersRegistrationId(Integer.valueOf(Helper.flagReturn.getText(line, 1))),
                                Player.FLAG_RETURN, 1, null); //+1 kill to the killer
                    } else if (lineType == Helper.FLAG_STEAL) {
                        currentGame.setPlayer(currentGame.getPlayersRegistrationId(Integer.valueOf(Helper.flagSteal.getText(line, 1))),
                                Player.FLAG_STEAL, 1, null); //+1 kill to the killer
                    } else if (lineType == Helper.GAME_RESULT_LINE) {
                        currentGame.appendGameResult(Helper.gameResult.getText(line, 1));
                    } else if (lineType == Helper.KILL_LINE) {
                        //kills and deaths are added and not set
                        if (!Helper.playerKill.getText(line, 1).contains("world")) {
                            currentGame.setPlayer(Helper.sanitizeName(Helper.playerKill.getText(line, 1)), Player.KILL, 1, Helper.playerKill.getText(line, 3)); //+1 kill to the killer
                            currentGame.setPlayer(Helper.sanitizeName(Helper.playerKill.getText(line, 2)), Player.DEATH, 1, null);//+1 death to the victim
                            currentGame.setGameTotalDeaths(currentGame.getGameTotalDeaths() + 1);
                        }
                    } else if (lineType == Helper.SCORE_LINE) {
                        //add the score (not added but set)
                        currentGame.setPlayer(Helper.sanitizeName(Helper.playerScore.getText(line, 2)), Player.SCORE, Integer.valueOf(Helper.playerScore.getText(line, 1)), null);
                        currentGame.setGameTotalScore(currentGame.getGameTotalScore() + Integer.valueOf(Helper.playerScore.getText(line, 1)));
                    }
                }
            }
            games.add(games.getSummaryGame(excludedPlayers)); //add summary of all games as the final game
        } catch (Exception e) {
            throw e;
        }
        return games;
    }

    private static int getLineType(String line) {

        //TODO KILL + DEATH + WEAPON -  19:44 Kill: 9 2 17: RODOV killed Seb by UT_MOD_UMP45
        //TODO GAME START -   0:29 InitRound: \sv_allowdownlo...
        //TODO GAME END -  20:44 ShutdownGame:
        //TODO GAME END REASON -  20:23 Exit: Timelimit hit.
        //TODO GAME SCORE X PLAYER -  20:23 score: 73  ping: 6  client: 5 Seb
        //TODO GAME RESULT - 20:28 red:3  blue:2
        if (Helper.playerKill.test(line))
            return Helper.KILL_LINE;
        if (Helper.playerScore.test(line))
            return Helper.SCORE_LINE;
        if (Helper.gameStart.test(line))
            return Helper.GAME_START_LINE;
        if (Helper.gameEnd.test(line))
            return Helper.GAME_END_LINE;
        if (Helper.gameEndReason.test(line))
            return Helper.GAME_END_REASON_LINE;
        if (Helper.gameResult.test(line))
            return Helper.GAME_RESULT_LINE;
        if (Helper.flagCapture.test(line))
            return Helper.FLAG_CAPTURE;
        if (Helper.flagReturn.test(line))
            return Helper.FLAG_RETURN;
        if (Helper.flagSteal.test(line))
            return Helper.FLAG_STEAL;
        if (Helper.playerRegistration.test(line))
            return Helper.PLAYER_REGISTRATION;
        return 0;
    }

    public static String sanitizeName(String name){
        if(name.startsWith("RODOV")){
            return "RODOV";
        }
        return name;
    }

    /***
     * Filters out exluded players by matching these player names to a regex that is supplied via CLI
     * @param excluded
     * @param playerName
     * @return comapre result
     */
    public static boolean isExcluded(String excluded, String playerName){
        //playerName = playerName.replaceAll("\\*|\\[|\\]|\\?|\\.|\\+|\\{|\\}|\\(|\\)|\\$|\\^|\\+|\\|","_");
        return playerName.matches(excluded);
    }
}
