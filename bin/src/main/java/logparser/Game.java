package logparser;

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
    private int gameRounds;
    private HashMap<String, Player> players;
    private HashMap<Integer, String> playersRegister;

    private final static String[] GAME_TYPES =
            {"Free for All",
                    "Uknown",
                    "Uknown",
                    "Team Deathmatch", //3
                    "Team Survivor", //4
                    "Follow the Leader", //5
                    "Capture & Hold", //6
                    "Capture The Flag", //7
                    "Bomb & Defuse", //8
                    "Jump Mode", //9
                    "Freeze Tag"}; //10

    public Game(String gameId) {
        setGameDate(new Date());
        this.gameId = gameId;
        this.gameRounds = 0;
        this.gameResult = "";
        this.setPlayers(new HashMap<String, Player>());
        this.playersRegister = new HashMap<Integer, String>();
    }

    public Game(String gameId, String gameDefLine) {
        this(gameId);
        setGameDate(new Date());
        this.init(gameDefLine);
    }

    public void init(String line) {
        setMapName(Helper.initGetMap.getText(line, 1).replace("ut4_", ""));
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
     *
     * @param player - player to merge
     */
    public void mergePlayer(Player player, String gameName) {
        if (players.containsKey(player.getName())) {
            Player basePlayer = players.get(player.getName());
            basePlayer.setGamesPlayed(basePlayer.getGamesPlayed() + 1);
            basePlayer.setDeaths(basePlayer.getDeaths() + player.getDeaths());
            basePlayer.setKills(basePlayer.getKills() + player.getKills());
            basePlayer.setScore(basePlayer.getScore() + player.getScore());
            basePlayer.setFlagCaptures(basePlayer.getFlagCaptures() + player.getFlagCaptures());
            basePlayer.setFlagReturns(basePlayer.getFlagReturns() + player.getFlagReturns());
            basePlayer.setFlagSteals(basePlayer.getFlagSteals() + player.getFlagSteals());
            //add history for the chart
            basePlayer.getHistory().add(new GameStats(gameName, (player.getDeaths() + player.getKills() > 0) ? (2 * ((double) player.getKills() / ((double) player.getDeaths() + player.getKills()))) : 0, player.getScore())); //
        }
    }

    public void addPlayer(Player player, String gameName) {
        if (!players.containsKey(player.getName())) {
            players.put(player.getName(), new Player(player.getName()));
        }
        mergePlayer(player, gameName);

    }

    public void setPlayer(String name, int valueType, int value, String weapon) {
        Player player;
        if (name == null || name.isEmpty()) {
            return;
        }
        if (!this.players.containsKey(name)) {
            players.put(name, new Player(name));
        }

        player = players.get(name);

        if (valueType == Player.DEATH) {
            player.setDeaths(player.getDeaths() + value);
        } else if (valueType == Player.SCORE) {
            player.addScore(value);
        } else if (valueType == Player.FLAG_RETURN) {
            player.setFlagReturns(player.getFlagReturns() + value);
        } else if (valueType == Player.FLAG_CAPTURE) {
            player.setFlagCaptures(player.getFlagCaptures() + value);
        } else if (valueType == Player.FLAG_STEAL) {
            try {
                player.setFlagSteals(player.getFlagSteals() + value);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (valueType == Player.KILL) {
            player.setKills(player.getKills() + value);
            if (weapon != null) {
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

    public int getGameTypeId() {
        int ret = -1;
        for (int i = 0; i < GAME_TYPES.length; i++) {
            if (GAME_TYPES[i].equalsIgnoreCase(this.getGameType())) {
                return i;
            }

        }
        return ret;
    }

    public void setGameType(int gameTypeId) {
        try {
            setGameType(GAME_TYPES[gameTypeId]);
        } catch (Exception e) {
            setGameType("Unknown");
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

    public void appendGameResult(String gameResult) {
        setGameRounds(getGameRounds() + 1);
        this.gameResult += "Round " + this.getGameRounds() + ": " + gameResult + " ";
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

    public int getGameRounds() {
        return gameRounds;
    }

    public void setGameRounds(int gameRounds) {
        this.gameRounds = gameRounds;
    }

    public String getPlayersRegistrationId(Integer id) {
        return playersRegister.get(id);
    }

    public void registerPlayer(Integer id, String name) {
        if (name == null || name.isEmpty()) {
            return;
        }
        this.playersRegister.put(id, name);
    }
}
