package com.github.mattboschetti.kingdom.scores.handlers;

import com.github.mattboschetti.kingdom.http.handler.CustomRequestHandler;
import com.github.mattboschetti.kingdom.scores.model.Game;
import com.github.mattboschetti.kingdom.scores.model.Score;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mattboschetti on 10/27/14.
 */
public class HighScoreHandler extends CustomRequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (!isGet(httpExchange)) {
            writePageNotFoundMessage(httpExchange);
            return;
        }

        int levelId;
        try {
            levelId = Integer.parseInt(httpExchange.getRequestURI().getPath().split("/")[1]);
        } catch (NumberFormatException e) {
            writeServerErrorMessage(httpExchange, "Invalid levelid format");
            return;
        }

        writeOk(httpExchange, getHighScores(levelId));
    }

    private String getHighScores(int levelId) {
        List<Score> scores = Game.getInstance().getHighScores(levelId);
        StringBuilder scoresCsv = new StringBuilder();
        for (Iterator<Score> i = scores.iterator(); i.hasNext(); ) {
            scoresCsv.append(i.next());
            if (i.hasNext())
                scoresCsv.append(",");
        }
        return scoresCsv.toString();
    }

}
