package me.rodov.urtlog;

import java.util.HashMap;

/**
 * Created by rudov on 29/02/2016.
 */
public class Player {
    private int deaths;
    private int kills;
    private int gamesPlayed;
    private int score;
    private String name;
    private String team;
    private HashMap<String, Integer> weapons;

    //todo replace by enum
    public final static int DEATH = 1;
    public final static int KILL = 2;
    public final static int SCORE = 3;

    public Player() {
        deaths = 0;
        kills = 0;
        score = 0;
        gamesPlayed = 1;
        weapons = new HashMap<String, Integer>();
    }

    public Player(String name) {
        this();
        this.name = name;
    }

    public Player(int deaths, int kills, int gamesPlayed, int score, String name) {
        this(name);
        this.deaths = deaths;
        this.kills = kills;
        this.gamesPlayed = gamesPlayed;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public HashMap<String, Integer> getWeapons() {
        return weapons;
    }

    public void setWeapons(HashMap<String, Integer> weapons) {
        this.weapons = weapons;
    }

    public void addToWeapons(String weapon) {
        if (!this.weapons.containsKey(weapon)) {
            weapons.put(weapon, 1);
        } else {
            weapons.put(weapon, weapons.get(weapon)+1);
        }
    }
}
