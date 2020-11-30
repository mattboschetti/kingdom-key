package com.github.mattboschetti.kingdom.scores.handlers;

import com.github.mattboschetti.kingdom.session.Session;
import com.github.mattboschetti.kingdom.http.handler.CustomRequestHandler;
import com.github.mattboschetti.kingdom.scores.model.Game;
import com.github.mattboschetti.kingdom.scores.model.Score;
import com.github.mattboschetti.kingdom.scores.model.User;
import com.github.mattboschetti.kingdom.session.Key;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by mattboschetti on 10/27/14.
 */
public class AddScoreHandler extends CustomRequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (!isPost(httpExchange)) {
            writePageNotFoundMessage(httpExchange);
            return;
        }

        Key sessionKey = authenticate(httpExchange);
        if (sessionKey == null)
            return;

        int levelId;
        try {
            levelId = Integer.parseInt(httpExchange.getRequestURI().getPath().split("/")[1]);
        } catch (NumberFormatException e) {
            writeServerErrorMessage(httpExchange, "Invalid levelid format");
            return;
        }

        int score;
        try {
            score = Integer.parseInt(readStream(httpExchange.getRequestBody()));
        } catch (NumberFormatException e) {
            writeServerErrorMessage(httpExchange, "Invalid score format");
            return;
        }

        User user = Session.getInstance().getUser(sessionKey);
        Game.getInstance().addScore(levelId, new Score(score, user));
        writeOk(httpExchange);
    }



}
