package me.rodov.q3log;

import java.util.*;

/**
 * Created by rudov on 21/02/2016.
 */
public class Game {
    private String gameId;
    private String mapName;
    private String gameType;
    private String gameResult;
    private String length;
    private String gameEndReason;
    private Date gameDate;
    private HashMap<String, Player> players;
    private final static String[] GAME_TYPES =
            {"Unknown",
                    "Last Man Standing",
                    "Free for All",
                    "Team Deathmatch",
                    "Team Survivor",
                    "Follow the Leader",
                    "Capture & Hold",
                    "Capture The Flag",
                    "Bomb & Defuse",
                    "Jump Mode",
                    "Freeze Tag"};

    public Game(String gameId) {
        setGameDate(new Date());
        this.gameId = gameId;
        this.setPlayers(new HashMap<String, Player>());
    }

    public Game(String gameId, String gameDefLine) {
        this(gameId);
        setGameDate(new Date());
        this.init(gameDefLine);
    }

    public void init(String line) {
        setMapName(Helper.initGetMap.getText(line, 1));
        appendGameId(getMapName());
        setGameType(Integer.valueOf(Helper.initGetGameType.getText(line, 1)));
        setLength(Helper.initGetGameTimeLimit.getText(line, 1));
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setPlayer(String name) {
        if (!this.players.containsKey(name)) {
            players.put(name, new Player(name));
        }
    }

    public void setPlayer(String name, int valueType, int value, String weapon) {
        Player player;
        if (!this.players.containsKey(name)) {
            players.put(name, new Player(name));
        }

        player = players.get(name);

        if (valueType == Player.DEATH) {
            player.setDeaths(player.getDeaths() + value);
        } else if (valueType == Player.SCORE) {
            player.setScore(value);
        } else if (valueType == Player.KILL) {
            player.setKills(player.getKills() + value);
            if(weapon != null){
                player.addToWeapons(weapon);
            }

        }


    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void appendGameId(String appendix) {
        this.setGameId(this.gameId + appendix);
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(int gameTypeId) {
        try {
            setGameType(GAME_TYPES[gameTypeId]);
        } catch (Exception e) {
            setGameType(GAME_TYPES[0]);
        }
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameEndReason() {
        return gameEndReason;
    }

    public void setGameEndReason(String gameEndReason) {
        this.gameEndReason = gameEndReason;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

    public String getGameResult() {
        return gameResult;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }
}
