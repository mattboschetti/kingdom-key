package com.github.mattboschetti.kingdom.scores.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mattboschetti on 10/27/14.
 */
public class Game {

    private Map<Integer, Level> levels = new ConcurrentHashMap<Integer, Level>();

    private static Game instance;

    public static synchronized Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public synchronized void addScore(int levelId, Score score) {
        Level level = getOrCreateLevel(levelId);
        level.addScore(score);
    }

    private synchronized Level getOrCreateLevel(int levelId) {
        if (levels.containsKey(levelId))
            return levels.get(levelId);

        Level level = new Level(levelId);
        levels.put(levelId, level);
        return level;
    }

    public synchronized List<Score> getHighScores(int levelId) {
        Level level = getOrCreateLevel(levelId);
        return level.highestScores();
    }

}
