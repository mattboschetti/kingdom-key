package com.github.mattboschetti.kingdom.session;

import com.github.mattboschetti.kingdom.scores.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mattboschetti on 10/25/14.
 */
public class Session {

    private static final long TEN_MINUTES = 1000 * 60 * 10;
    private final long EXPIRY_TIME;
    private Map<Key, Expiration> keysExpiration = new ConcurrentHashMap<Key, Expiration>();
    private Map<Key, User> keysUsers = new ConcurrentHashMap<Key, User>();

    private static Session instance;

    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    Session() {
        this.EXPIRY_TIME = TEN_MINUTES;
    }

    Session(long expiryTime) {
        this.EXPIRY_TIME = expiryTime;
    }

    public synchronized Key createSession(User user) {
        Key key = new Key();
        keysExpiration.put(key, new Expiration(EXPIRY_TIME));
        keysUsers.put(key, user);
        return key;
    }

    public synchronized boolean isSessionExpired(Key key) {
        if (!keysExpiration.containsKey(key))
            return true;

        if (!keysExpiration.get(key).isExpired())
            return false;

        keysExpiration.remove(key);
        keysUsers.remove(key);
        return true;
    }

    public synchronized User getUser(Key key) {
        if (!keysUsers.containsKey(key))
            return null;
        return keysUsers.get(key);
    }

}
