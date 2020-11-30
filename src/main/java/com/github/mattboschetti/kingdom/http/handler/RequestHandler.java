package com.github.mattboschetti.kingdom.http.handler;

import com.github.mattboschetti.kingdom.http.Router;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by mattboschetti on 10/27/14.
 */
public class RequestHandler implements HttpHandler {

    private Router<HttpHandler> router;

    public RequestHandler(Router<HttpHandler> router) {
        this.router = router;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        router.route(path).handle(httpExchange);
    }


}


