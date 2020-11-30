package com.github.mattboschetti.kingdom;

import com.github.mattboschetti.kingdom.http.Router;
import com.github.mattboschetti.kingdom.http.filter.QueryStringFilter;
import com.github.mattboschetti.kingdom.http.handler.NotFoundHandler;
import com.github.mattboschetti.kingdom.http.handler.RequestHandler;
import com.github.mattboschetti.kingdom.scores.handlers.AddScoreHandler;
import com.github.mattboschetti.kingdom.scores.handlers.HighScoreHandler;
import com.github.mattboschetti.kingdom.scores.handlers.LoginHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        Router<HttpHandler> router = new Router<HttpHandler>();
        router.addRoute("/(.*)/login(/?(\\?.*)?)$", new LoginHandler())
                .addRoute("/(.*)/score(/?(\\?.*)?)$", new AddScoreHandler())
                .addRoute("/(.*)/highscorelist(/?(\\?.*)?)$", new HighScoreHandler())
                .notFound(new NotFoundHandler());
        RequestHandler handler = new RequestHandler(router);

        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", handler).getFilters().add(new QueryStringFilter());
        server.setExecutor(Executors.newFixedThreadPool(15));
        server.start();
        System.out.println("Server started. Listening port 8081.");
    }

}
