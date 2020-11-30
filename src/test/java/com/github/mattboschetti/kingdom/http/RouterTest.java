package com.github.mattboschetti.kingdom.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RouterTest {

    @Test
    public void testRoute() throws Exception {
        Router<String> router = createRouter();

        String result = router.route("/1234/score");

        assertEquals("score route", result);
    }

    @Test
    public void testRouteWhenPathHasMoreElements() throws Exception {
        Router<String> router = createRouter();

        String result = router.route("/1234/score/more/path");

        assertEquals("route not found", result);
    }

    @Test
    public void testRouteWhenQueryStringPresent() throws Exception {
        Router<String> router = createRouter();

        String result = router.route("/1234/score?query=string");

        assertEquals("score route", result);
    }

    @Test
    public void testRouteWhenQueryStringPresentFinishingWithSlash() throws Exception {
        Router<String> router = createRouter();

        String result = router.route("/1234/score/?query=string");

        assertEquals("score route", result);
    }

    @Test
    public void testRouteWhenThereIsNoId() throws Exception {
        Router<String> router = createRouter();

        String result = router.route("/score");

        assertEquals("route not found", result);
    }

    private Router createRouter() {
        Router<String> router = new Router<String>();
        router.addRoute("/(.*)/score(/?(\\?.*)?)$", "score route").notFound("route not found");
        return router;
    }
}