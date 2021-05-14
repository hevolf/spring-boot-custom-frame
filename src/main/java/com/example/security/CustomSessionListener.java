package com.example.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * @author caohaifengx@163.com 2021-05-13 15:27
 */
@Slf4j
public class CustomSessionListener implements SessionListener {
    @Override
    public void onStart(Session session) {
        log.info("CustomSessionListener.onStart:id#{}", session.getId());
    }

    @Override
    public void onStop(Session session) {
        log.info("CustomSessionListener.onStop:id#{}", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        log.info("CustomSessionListener.onExpiration:id#{}", session.getId());
    }
}
