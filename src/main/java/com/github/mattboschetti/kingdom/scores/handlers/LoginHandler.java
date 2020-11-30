package com.github.mattboschetti.kingdom.scores.handlers;

import com.github.mattboschetti.kingdom.session.Session;
import com.github.mattboschetti.kingdom.http.handler.CustomRequestHandler;
import com.github.mattboschetti.kingdom.scores.model.User;
import com.github.mattboschetti.kingdom.session.Key;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by mattboschetti on 10/27/14.
 */
public class LoginHandler extends CustomRequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (!httpExchange.getRequestMethod().equals("GET")) {
            httpExchange.sendResponseHeaders(404,0);
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(httpExchange.getRequestURI().getPath().split("/")[1]);
        } catch (NumberFormatException e) {
            writeServerErrorMessage(httpExchange, "Invalid userid format");
            return;
        }

        Key key = Session.getInstance().createSession(new User(userId));
        writeOk(httpExchange, key.getValue());
    }

}
