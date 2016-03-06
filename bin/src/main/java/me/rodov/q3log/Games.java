package me.rodov.q3log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rudov on 21/02/2016.
 */
public class Games {
    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Game> games;

    public Games() {
        games = new ArrayList<Game>();
    }
    public void add(Game game){
        games.add(game);
    }
}
