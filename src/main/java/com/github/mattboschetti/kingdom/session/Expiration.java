package com.github.mattboschetti.kingdom.session;

import java.util.Date;

/**
 * Created by mattboschetti on 10/25/14.
 */
final public class Expiration {

    private final long createdAt;
    private final long expiryTime;

    public Expiration(long expiryTime) {
        this.expiryTime = expiryTime;
        createdAt = new Date().getTime();
    }

    public boolean isExpired() {
        long now = new Date().getTime();
        return now - createdAt > expiryTime;
    }

}
