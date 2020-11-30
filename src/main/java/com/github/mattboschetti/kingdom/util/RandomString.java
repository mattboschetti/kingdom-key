package com.github.mattboschetti.kingdom.util;

import java.util.Random;

/**
 * Created by mattboschetti on 10/25/14.
 */
public class RandomString {

    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvxz1234567890";
    private static final int MAX_RANDOM_INT = ALLOWED_CHARS.length();
    private static RandomString instance;

    private RandomString() {}

    public static synchronized RandomString getInstance() {
        if (instance == null)
            instance = new RandomString();
        return instance;
    }

    public synchronized String randomize(final int size) {
        Random randomize = new Random();
        StringBuilder randomValue = new StringBuilder();
        for (int i = 0; i < size; i++) {
            final int selectedChar = randomize.nextInt(MAX_RANDOM_INT);
            randomValue.append(ALLOWED_CHARS.charAt(selectedChar));
        }
        return randomValue.toString().toUpperCase();
    }
}
