package logparser;

/**
 * Created by Rodov on 22/05/17.
 */
public class GameStats {
    String name;
    double ratio;
    long score;

    public GameStats(String name, double ratio, long score) {
        this.name = name;
        this.ratio = Math.round(ratio*100)/100.0; //round to 2 digits after a decimal point
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
