package com.github.mattboschetti.kingdom.http;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by mattboschetti on 10/27/14.
 */
public class Router<T> {

    private Map<Pattern, T> routes = new HashMap<Pattern, T>();
    private T notFoundHandler;

    public Router addRoute(String path, T handler) {
        Pattern p = Pattern.compile(path);
        routes.put(p, handler);
        return this;
    }

    public Router notFound(T handler) {
        notFoundHandler = handler;
        return this;
    }

    public T route(String path) {
        for (Map.Entry<Pattern, T> route : routes.entrySet()) {
            if (route.getKey().matcher(path).matches())
                return route.getValue();
        }
        return notFoundHandler;
    }
}
