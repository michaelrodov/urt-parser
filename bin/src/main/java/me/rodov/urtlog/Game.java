package me.rodov.urtlog;

import java.util.*;

/**
 * Created by rudov on 21/02/2016.
 */
public class Game {
    private String gameId;
    private String mapName;
    private String gameType;
    private String gameResult;
    private int gameTotalDeaths;
    private int gameTotalScore;
    private String gameLength;
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
        setGameLength(Helper.initGetGameTimeLimit.getText(line, 1));
        setGameTotalDeaths(0);
        setGameTotalScore(0);
    }

    public String getGameLength() {
        return gameLength;
    }

    public void setGameLength(String gameLength) {
        this.gameLength = gameLength;
    }

    public void setPlayer(String name) {
        if (!this.players.containsKey(name)) {
            players.put(name, new Player(name));
        }
    }

    /***
     * TODO overload the + operator for Player
     * Sums the player stats if already exists
     * @param player - player to merge
     */
    public void mergePlayer(Player player){
        if(players.containsKey(player.getName())){
           Player basePlayer = players.get(player.getName());
            basePlayer.setGamesPlayed(basePlayer.getGamesPlayed()+1);
            basePlayer.setDeaths(basePlayer.getDeaths() + player.getDeaths());
            basePlayer.setKills(basePlayer.getKills() + player.getKills());
            basePlayer.setScore(basePlayer.getScore() + player.getScore());
        }
    }

    public void addPlayer(Player player){
        if(!players.containsKey(player.getName())){
            players.put(player.getName(), new Player(player.getName()));
        }
            mergePlayer(player);

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


    public int getGameTotalDeaths() {
        return gameTotalDeaths;
    }

    public void setGameTotalDeaths(int gameTotalDeaths) {
        this.gameTotalDeaths = gameTotalDeaths;
    }

    public int getGameTotalScore() {
        return gameTotalScore;
    }

    public void setGameTotalScore(int gameTotalScore) {
        this.gameTotalScore = gameTotalScore;
    }
}
