package me.rodov.q3log;

import ru.lanwen.verbalregex.VerbalExpression;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.Exception;

/**
 * Created by rudov on 29/02/2016.
 */
public class Helper {
    //test
    //todo replace by enum
    public final static int GAME_START_LINE = 1;
    public final static int GAME_END_LINE = 2;
    public final static int GAME_END_REASON_LINE = 3;
    public final static int SCORE_LINE = 4;
    public final static int KILL_LINE = 5;


    private final static String MAP = "mapname";
    private final static String TYPE = "g_gametype";
    private final static String HOSTNAME = "sv_hostname";
    private final static String TIMELIMIT = "timelimit";


    // 0:29 InitRound: \sv_allowdownlo...
    public static VerbalExpression gameStart = VerbalExpression.regex()
            .find("InitRound: ").capture().anything().endCapture().build();

    //20:44 ShutdownGame:
    public static VerbalExpression gameEnd = VerbalExpression.regex()
            .startOfLine().then(" ").capture().anything().endCapture().then(" ShutdownGame:").build();

    //20:23 Exit: Timelimit hit.
    public static VerbalExpression gameEndReason = VerbalExpression.regex()
            .find("Exit: ").capture().anything().endCapture().build();

    //20:23 score: 73  ping: 6  client: 5 Seb
    public static VerbalExpression playerScore = VerbalExpression.regex()
            .find("score: ").capture().digit().oneOrMore().endCapture().space().find("client: ")
            .digit().oneOrMore().space().capture().anything().endCapture().build();

    //19:44 Kill: 9 2 17: RODOV killed Seb by UT_MOD_UMP45
    public static VerbalExpression playerKill = VerbalExpression.regex()
            .find("Kill: ").anything().then(": ").capture().anything().endCapture().then(" killed ")
            .capture().anything().endCapture().then(" by ")
            .capture().anything().endCapture().build();

    //19:44 Kill: 9 2 17: RODOV killed Seb by UT_MOD_UMP45
    public static VerbalExpression playerDetails = VerbalExpression.regex()
            .find("ClientUserinfo: ").anything().then(": ").capture().anything().endCapture().then(" killed ")
            .capture().anything().endCapture().then(" by ")
            .capture().anything().endCapture().build();

    public static VerbalExpression initGetMap = VerbalExpression.regex().find("\\mapname\\").capture().anything().endCapture().then("\\sv_privateClients").build();
    public static VerbalExpression initGetHost = VerbalExpression.regex().find("\\sv_hostname\\").capture().anything().endCapture().then("\\").build();
    public static VerbalExpression initGetGameType = VerbalExpression.regex().find("\\g_gametype\\").capture().digit().oneOrMore().endCapture().then("\\").build();
    public static VerbalExpression initGetGameTimeLimit = VerbalExpression.regex().find("\\timelimit\\").capture().digit().oneOrMore().endCapture().then("\\").build();


    public static Games readLog(BufferedReader log) throws Exception {
        int inx = 1;
        String line = new String();
        Games games = new Games();
        Game currentGame = new Game("game" + inx); //create a new Game. the processing is linear so we can do it like this

        int lineType;

        try {
            //loop throughout the log
            while ((line = log.readLine()) != null) {
                if (!line.equals("")) {
                    lineType = getLineType(line); //analyze game type //TODO improve missing some states and too complicated

                    if (lineType == Helper.GAME_START_LINE) {
                        currentGame = new Game("game" + inx);
                        currentGame.init(line);
                    } else if (lineType == Helper.GAME_END_LINE) {
                        currentGame.setLength(Helper.gameEnd.getText(line, 1));
                        games.add(currentGame); //add a new Game to games list
                        inx++;
                    } else if (lineType == Helper.GAME_END_REASON_LINE) {
                        currentGame.setGameEndReason(Helper.gameEndReason.getText(line, 1));

                    } else if (lineType == Helper.KILL_LINE) {
                        //kills and deaths are added and not set
                        currentGame.setPlayer(Helper.playerKill.getText(line, 2), Players.KILL, 1); //+1 kill to the killer
                        currentGame.setPlayer(Helper.playerKill.getText(line, 1), Players.DEATH, 1);//+1 death to the victim
                        //TODO add weapons (Helper.playerKill.getText(line, 3) to the killer

                    } else if (lineType == Helper.SCORE_LINE) {
                        //add the score (not added but set)
                        currentGame.setPlayer(Helper.playerScore.getText(line, 2), Players.KILL, Integer.valueOf(Helper.playerScore.getText(line, 1)));
                    }

                }
            }
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
        if(Helper.playerKill.test(line))
            return Helper.KILL_LINE;
        if(Helper.playerScore.test(line))
            return Helper.SCORE_LINE;
        if(Helper.gameStart.test(line))
            return Helper.GAME_START_LINE;
        if(Helper.gameEnd.test(line))
            return Helper.GAME_END_LINE;
        if(Helper.gameEndReason.test(line))
            return Helper.GAME_END_REASON_LINE;

        return 0;
    }

}
