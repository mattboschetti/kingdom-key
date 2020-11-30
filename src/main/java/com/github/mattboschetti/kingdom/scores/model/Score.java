package com.github.mattboschetti.kingdom.scores.model;

/**
 * Created by mattboschetti on 10/25/14.
 */
final public class Score implements Comparable<Score> {

    private final int score;
    private final User user;

    public Score(int score, User user) {
        this.score = score;
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int compareTo(Score o) {
        return o.getScore() - score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        if (!user.equals(score.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public String toString() {
        return user.getId() + "=" + score;
    }
}
