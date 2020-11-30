package com.github.mattboschetti.kingdom.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mattboschetti on 10/27/14.
 */
public class QueryStringExtractor {

    public Map<String, String> extract(String query) {
        Map<String, String> result = new HashMap<String, String>();

        if (query == null)
            return result;

        String[] params = query.split("&");
        for (String param : params) {
            if(param.length() == 0)
                continue;

            String[] keyValue = param.split("=");
            if (keyValue.length == 2)
                result.put(keyValue[0], keyValue[1]);
            if (keyValue.length == 1)
                result.put(keyValue[0], null);
        }
        return result;
    }

}
