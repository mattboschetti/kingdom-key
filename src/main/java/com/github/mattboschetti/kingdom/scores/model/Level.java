package com.github.mattboschetti.kingdom.scores.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by mattboschetti on 10/25/14.
 */
public class Level {

    private static final int DEFAULT_SCORE_SIZE = 15;
    private final int levelId;
    private final int MAX_SCORE_SIZE;
    private List<Score> scores = Collections.synchronizedList(new ArrayList<Score>());
    private int lowestScore = 0;

    public Level(int levelId) {
        this.levelId = levelId;
        MAX_SCORE_SIZE = DEFAULT_SCORE_SIZE;
    }

    public Level(int levelId, int scoreSize) {
        this.levelId = levelId;
        this.MAX_SCORE_SIZE = scoreSize;
    }

    public synchronized void addScore(Score newScore) {
        if (isScoreListFull() && isLowestScoreLowerThan(newScore))
            return;

        if (isUserAlreadyInHighScores(newScore)) {
            Score currentScore = getCurrentUserScore(newScore.getUser());
            if (currentScore.getScore() >= newScore.getScore())
                return;
            scores.remove(currentScore);
        }

        scores.add(newScore);
        highScoreListCleanUp();
    }

    private synchronized void highScoreListCleanUp() {
        Collections.sort(scores);
        scores = trimScoresList(scores, MAX_SCORE_SIZE);
        lowestScore = findLowestScore(scores);
    }

    private synchronized List<Score> trimScoresList(List<Score> scores, int maximumListSize) {
        int listEnd = scores.size() < maximumListSize ? scores.size() : maximumListSize;
        return scores.subList(0, listEnd);
    }

    private synchronized int findLowestScore(List<Score> scores) {
        try {
            return Collections.min(scores).getScore();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    private synchronized boolean isLowestScoreLowerThan(Score newScore) {
        return newScore.getScore() < lowestScore;
    }

    private synchronized boolean isScoreListFull() {
        return scores.size() == MAX_SCORE_SIZE;
    }

    private synchronized boolean isUserAlreadyInHighScores(Score newScore) {
        return scores.contains(newScore);
    }

    private synchronized Score getCurrentUserScore(User user) {
        for(Score score : scores) {
            if (score.getUser().equals(user))
                return score;
        }
        return null;
    }

    public List<Score> highestScores() {
        return scores;
    }

    public int getLevelId() {
        return levelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level level = (Level) o;

        if (levelId != level.levelId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return levelId;
    }
}
