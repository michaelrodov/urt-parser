package me.rodov.q3log;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by rudov on 21/02/2016.
 */
public class Games {
    public HashMap<String, Game> games;

    public Games() {
        games = new HashMap<String, Game>();
    }

    public void add(Game game) {
        games.put(game.getGameId(), game);
    }

    public Game getSummaryGame() {
        Game summary = new Game("SUMMARY");
        //TODO add with operator overload and
        for (Game game : this.games.values()) {
            for (Player player : game.getPlayers().values()) {
                summary.addPlayer(player);
            }
            summary.setGameTotalDeaths(summary.getGameTotalDeaths() + game.getGameTotalDeaths());
            summary.setGameTotalScore(summary.getGameTotalScore() + game.getGameTotalScore());
        }
        summary.setGameLength("0");
        summary.setGameType("SUMMARY");
        summary.setGameDate(new Date());
        summary.setGameEndReason("-");
        summary.setGameResult("");
        summary.setMapName("SUMMARY");

        return summary;
    }


    public HashMap<String, Game> getGames() {
        return games;
    }

    public void setGames(HashMap<String, Game> games) {
        this.games = games;
    }

}
