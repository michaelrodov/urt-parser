package me.rodov.q3log;

/**
 * Created by rudov on 29/02/2016.
 */
public class Players {
    private int deaths;
    private int kills;
    private int type;
    private int score;
    private String name;
    private String team;

    //todo replace by enum
    public final static int DEATH = 1;
    public final static int KILL = 2;
    public final static int SCORE = 3;

    public Players(){
        deaths=0;
        kills=0;
        score=0;
    }
    public Players(String name) {
        this();
        this.name = name;
    }

    public Players(int deaths, int kills, int type, int score, String name) {
        this(name);
        this.deaths = deaths;
        this.kills = kills;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
