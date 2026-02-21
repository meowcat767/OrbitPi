package site.meowcat.managers;

import java.io.Serializable;

public class LeaderboardEntry implements Serializable {
    private final String name;
    private final int score;
    private final long timestamp;

    public LeaderboardEntry(String name, int score) {
        this.name = name;
        this.score = score;
        this.timestamp = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
