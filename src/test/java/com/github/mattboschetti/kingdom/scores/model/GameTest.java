package com.github.mattboschetti.kingdom.scores.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testAddScore() {
        Game game = new Game();

        game.addScore(1, createScore(100, 10));

        List<Score> highScores = game.getHighScores(1);
        assertNotNull(highScores);
        assertEquals(1, highScores.size());
        assertEquals(10, highScores.get(0).getScore());
        assertEquals(100, highScores.get(0).getUser().getId());
    }

    @Test
    public void gettingHighScoresForNonExistentScoreShouldReturnEmptyList() {
        Game game = new Game();

        List<Score> highScores = game.getHighScores(1);

        assertNotNull(highScores);
        assertTrue(highScores.isEmpty());
    }

    private Score createScore(int userId, int score) {
        return new Score(score, new User(userId));
    }
}