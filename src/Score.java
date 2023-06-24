public class Score {
    private String name;
    private long score;

    // Constructor of the class with parameters
    public Score (String name, long score) {
        this.name = name;
        this.score = score;
    }

    // Name getter
    public String getName () {
        return name;
    }

    // Name setter
    public void setName (String name) {
        this.name = name;
    }

    // Score getter
    public long getScore () {
        return score;
    }

    // Score setter
    public void setScore (long score) {
        this.score = score;
    }
}