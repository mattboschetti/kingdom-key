package com.github.mattboschetti.kingdom.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mattboschetti on 10/25/14.
 */
public class RandomStringTest {

    @Test
    public void twoConsecutiveCallsToRandomizeShouldReturnDifferentValues() {
        String s1 = RandomString.getInstance().randomize(10);
        String s2 = RandomString.getInstance().randomize(10);
        Assert.assertNotEquals(s1, s2);
    }

    @Test
    public void randomValueShouldHaveGivenSize() {
        int expectedSize = 5;
        String s1 = RandomString.getInstance().randomize(expectedSize);
        Assert.assertEquals(expectedSize, s1.length());
    }

}
