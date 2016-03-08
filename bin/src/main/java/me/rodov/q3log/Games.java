package me.rodov.q3log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rudov on 21/02/2016.
 */
public class Games {
   public HashMap<String, Game> games;

    public Games() {
        games = new HashMap<String, Game>();
    }

    public void add(Game game){
        games.put(game.getGameId(), game);
    }

    public HashMap<String, Game> getGames() {
        return games;
    }
    public void setGames(HashMap<String, Game> games) {
        this.games = games;
    }

}
