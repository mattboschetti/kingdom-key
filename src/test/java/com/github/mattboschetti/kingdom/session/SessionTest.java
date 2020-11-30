package com.github.mattboschetti.kingdom.session;

import com.github.mattboschetti.kingdom.scores.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionTest {

    @Test
    public void createSessionIsReturningKey() {
        User user = new User(1234);
        Session session = new Session();

        Key key = session.createSession(user);

        assertNotNull(key);
        assertNotNull(key.getValue());
    }

    @Test
    public void invalidKeyShouldReturnTrueWhenVerifyingExpiredSession() {
        Key key = new Key("ABC123D");
        Session session = new Session();

        boolean isExpired = session.isSessionExpired(key);

        assertTrue(isExpired);
    }

    @Test
    public void validKeyShouldNotHaveExpiredSessionBeforeTenMinutes() {
        User user = new User(1234);
        Session session = new Session();
        Key key = session.createSession(user);

        boolean isExpired = session.isSessionExpired(key);

        assertFalse(isExpired);
    }

    @Test
    public void validKeyShouldHaveSessionExpiredAfterExpiryTime() throws InterruptedException {
        User user = new User(1234);
        Session session = new Session(1);
        Key key = session.createSession(user);

        Thread.sleep(2);
        boolean isExpired = session.isSessionExpired(key);

        assertTrue(isExpired);
    }

    @Test
    public void userShouldBeRemovedForExpiredKey() throws InterruptedException {
        User user = new User(1234);
        Session session = new Session(1);
        Key key = session.createSession(user);
        Thread.sleep(2);
        session.isSessionExpired(key);

        user = session.getUser(key);

        assertNull(user);
    }

}