package com.github.mattboschetti.kingdom.http.handler;

import com.github.mattboschetti.kingdom.session.Key;
import com.github.mattboschetti.kingdom.session.Session;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by mattboschetti on 10/27/14.
 */
public class CustomRequestHandler {

    private final String SESSION_KEY = "sessionkey";

    public boolean isGet(HttpExchange httpExchange) {
        return isRequestMethodEquals(httpExchange, "GET");
    }

    public boolean isPost(HttpExchange httpExchange) {
        return isRequestMethodEquals(httpExchange, "POST");
    }

    public boolean isRequestMethodEquals(HttpExchange httpExchange, String method) {
        return httpExchange.getRequestMethod().equalsIgnoreCase(method);
    }

    public void writeServerErrorMessage(HttpExchange httpExchange, String message) throws IOException {
        writeReponse(httpExchange, 500, message);
    }

    public void writeUnauthorizedMessage(HttpExchange httpExchange) throws IOException {
        writeReponse(httpExchange, 401, "Unauthorized acess");
    }

    public void writePageNotFoundMessage(HttpExchange httpExchange) throws IOException {
        writeReponse(httpExchange, 404, "Page not found, verify HTTP method or URL");
    }

    public void writeOk(HttpExchange httpExchange) throws IOException {
        writeReponse(httpExchange, 200, "");
    }

    public void writeOk(HttpExchange httpExchange, String message) throws IOException {
        writeReponse(httpExchange, 200, message);
    }

    public String readStream(InputStream inputStream) {
        StringBuilder response = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNext())
            response.append(scanner.next());

        scanner.close();
        return response.toString();
    }

    private void writeReponse(HttpExchange httpExchange, int httpStatus, String message) throws IOException {
        byte[] reponse = message.getBytes();
        httpExchange.sendResponseHeaders(httpStatus, reponse.length);
        httpExchange.getResponseBody().write(reponse);
        httpExchange.close();
    }

    public Key authenticate(HttpExchange httpExchange) throws IOException {
        Map<String, String> params = (Map<String, String>) httpExchange.getAttribute("queryParams");
        if (!params.containsKey(SESSION_KEY)) {
            writeUnauthorizedMessage(httpExchange);
            return null;
        }

        Key sessionKey = new Key(params.get(SESSION_KEY));
        if (Session.getInstance().isSessionExpired(sessionKey)) {
            writeUnauthorizedMessage(httpExchange);
            return null;
        }
        return sessionKey;
    }

}
