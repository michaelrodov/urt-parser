package me.rodov.q3log;

import ru.lanwen.verbalregex.VerbalExpression;

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
    public final static int KILL_LsINE = 5;

    
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


/*
    public static String getGameType(String gameDefLine){
        return GAME_TYPES[Integer.valueOf(getParamFromLine(TYPE, gameDefLine))];
    }
    public static String getGameMap(String gameDefLine){
        return getParamFromLine(MAP, gameDefLine);
    }
    public static String getGameTimelimit(String gameDefLine){
        return getParamFromLine(TIMELIMIT, gameDefLine);
    }
    public static String getGameHostname(String gameDefLine){
        return getParamFromLine(HOSTNAME, gameDefLine);
    }
    private static String getParamFromLine(String param, String line){
        if(!param.isEmpty() && !line.isEmpty()) {
            int valueStart = line.indexOf("\\" + param + "\\") + param.length() + 2;
            int valueEnd = valueStart + line.indexOf("\\", valueStart);
            return line.substring(valueStart, valueEnd);
        }else{
            return null;
        }
    }
*/


}
