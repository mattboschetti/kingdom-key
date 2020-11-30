package com.github.mattboschetti.kingdom.session;

import com.github.mattboschetti.kingdom.util.RandomString;

/**
 * Created by mattboschetti on 10/25/14.
 */
final public class Key {

    private static final int KEY_SIZE = 7;
    private final String value;

    public Key() {
        this.value = RandomString.getInstance().randomize(KEY_SIZE);
    }

    public Key(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (value != null ? !value.equals(key.value) : key.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
