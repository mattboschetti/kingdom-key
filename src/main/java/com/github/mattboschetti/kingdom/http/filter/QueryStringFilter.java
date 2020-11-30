package com.github.mattboschetti.kingdom.http.filter;

import com.github.mattboschetti.kingdom.http.QueryStringExtractor;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

/**
 * Created by mattboschetti on 10/27/14.
 */
public class QueryStringFilter extends Filter {

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
        Map<String, String> params = new QueryStringExtractor().extract(httpExchange.getRequestURI().getQuery());
        httpExchange.setAttribute("queryParams", params);
        chain.doFilter(httpExchange);
    }

    @Override
    public String description() {
        return "Filter that breaks down query parameters";
    }
}
