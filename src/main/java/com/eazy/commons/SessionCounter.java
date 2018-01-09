package com.eazy.commons;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 实现在线人数统计
 */
public class SessionCounter implements HttpSessionListener {

    private static int activeSessions = 0;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        SessionCounter.activeSessions++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        if (SessionCounter.activeSessions > 0)
            SessionCounter.activeSessions--;
    }

    public static int getActiveSessions() {
        return activeSessions;
    }
}
