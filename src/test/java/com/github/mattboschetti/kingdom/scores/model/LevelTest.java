package com.github.mattboschetti.kingdom.scores.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LevelTest {

    @Test
    public void addScore() {
        Level level = new Level(1);

        level.addScore(createScore(1, 100));
        List<Score> scores = level.highestScores();

        assertNotNull(scores);
        assertEquals(1, scores.size());
        assertEquals(1, scores.get(0).getScore());
        assertEquals(100, scores.get(0).getUser().getId());
    }

    @Test
    public void addingBiggerScoreForSameUserShouldReplaceValue() {
        Level level = new Level(1);
        level.addScore(createScore(1, 100));
        level.addScore(createScore(5, 100));

        List<Score> scores = level.highestScores();

        assertNotNull(scores);
        assertEquals(1, scores.size());
        assertEquals(100, scores.get(0).getUser().getId());
        assertEquals(5, scores.get(0).getScore());
    }

    @Test
    public void addingLowerScoreForSameUserShouldKeepCurrentValue() {
        Level level = new Level(1);
        level.addScore(createScore(5, 100));
        level.addScore(createScore(1, 100));

        List<Score> scores = level.highestScores();

        assertNotNull(scores);
        assertEquals(1, scores.size());
        assertEquals(100, scores.get(0).getUser().getId());
        assertEquals(5, scores.get(0).getScore());
    }

    @Test
    public void highestScoresListShouldHaveNumberOfElementsOrLess() {
        Level level = new Level(1, 3);

        level.addScore(createScore(1, 100));
        level.addScore(createScore(5, 101));
        level.addScore(createScore(5, 102));
        level.addScore(createScore(2, 103));

        List<Score> scores = level.highestScores();

        assertNotNull(scores);
        assertEquals(3, scores.size());
    }

    @Test
    public void sameUserCannotAppearTwiceInHighScores() {
        Level level = new Level(1, 4);

        level.addScore(createScore(1, 100));
        level.addScore(createScore(5, 101));
        level.addScore(createScore(8, 102));
        level.addScore(createScore(5, 100));

        List<Score> scores = level.highestScores();

        assertNotNull(scores);
        assertEquals(3, scores.size());

        int user100Count = 0;
        for (Score score : scores) {
            if (score.getUser().getId() == 100)
                user100Count++;
        }
        assertEquals(1, user100Count);
    }

    @Test
    public void shouldNotAddScoreLowerThanLowestWhenScoreListIsFull() {
        Level level = new Level(1, 4);

        level.addScore(createScore(6, 100));
        level.addScore(createScore(5, 101));
        level.addScore(createScore(8, 102));
        level.addScore(createScore(5, 103));
        level.addScore(createScore(3, 104));

        List<Score> scores = level.highestScores();
        assertNotNull(scores);
        assertEquals(4, scores.size());

        boolean hasScore3ForUser104 = false;
        for (Score score : scores)
            hasScore3ForUser104 = score.getUser().getId() == 104;

        assertFalse(hasScore3ForUser104);
    }

    private Score createScore(int score, int user) {
        return new Score(score, new User(user));
    }

}