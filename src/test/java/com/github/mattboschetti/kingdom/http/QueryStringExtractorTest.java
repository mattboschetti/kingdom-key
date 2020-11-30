package com.github.mattboschetti.kingdom.http;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QueryStringExtractorTest {

    @Test
    public void testExtract() {
        QueryStringExtractor extractor = new QueryStringExtractor();
        Map<String, String> params = extractor.extract("a=b&c=d");
        assertEquals(2, params.size());
        assertEquals("b", params.get("a"));
        assertEquals("d", params.get("c"));
    }

    @Test
    public void testEmptyString() {
        QueryStringExtractor extractor = new QueryStringExtractor();
        Map<String, String> params = extractor.extract("");
        assertEquals(0, params.size());
    }

    @Test
    public void testParamThatOnlyHasKey() {
        QueryStringExtractor extractor = new QueryStringExtractor();
        Map<String, String> params = extractor.extract("a=");
        assertEquals(1, params.size());
        assertNull(params.get("a"));
    }

    @Test
    public void testEmptyParams() {
        QueryStringExtractor extractor = new QueryStringExtractor();
        Map<String, String> params = extractor.extract("&&&");
        assertEquals(0, params.size());
    }

    @Test
    public void testExtractEmptyValueAndNormalParams() {
        QueryStringExtractor extractor = new QueryStringExtractor();
        Map<String, String> params = extractor.extract("a=b&c=&");
        assertEquals(2, params.size());
        assertEquals("b", params.get("a"));
        assertNull(params.get("c"));
    }

}