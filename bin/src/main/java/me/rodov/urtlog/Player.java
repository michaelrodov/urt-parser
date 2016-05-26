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
        setName(Helper.sanitizeName(name));
    }

    public Player(int deaths, int kills, int gamesPlayed, int score, String name) {
        this(name);
        this.deaths = deaths;
        this.kills = kills;
        this.gamesPlayed = gamesPlayed;
        this.score = score;
    }

    public int getScore() {
        if (this.score != 0) {
            return score;
        } else {
            return kills;
        }
    }

    public void setScore(int score) {
        if (score == 0 && this.kills > 0) {
            this.score = this.kills;
        } else {
            this.score = score;
        }
    }

    public void addScore(int score){
        setScore(this.score + score);
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

    /***
     * Add weapons but remove all the banned (usually system notifications for a death type)
     * @param weapons
     */
    public void setWeapons(HashMap<String, Integer> weapons) {
        String[] bannedWeps = Helper.bannedWeapons.split(",");
        for (int i = 0; i < bannedWeps.length; i++) {
            weapons.remove(bannedWeps[i]);
        }
        this.weapons = weapons;
    }


    /***
     * Add to weapons list (+1) but remove the weaponse that were banned
     * @param weapon
     */
    public void addToWeapons(String weapon) {
        if (!Helper.bannedWeapons.contains(weapon)) {
            weapon = weapon.replace("UT_MOD_", "");
            if (!weapons.containsKey(weapon)) {
                weapons.put(weapon, 1);
            } else {
                weapons.put(weapon, weapons.get(weapon) + 1);
            }
        }
    }
}
