package com.forge.shiro.authentic;

import org.apache.shiro.authc.UsernamePasswordToken;


public class MyToken extends UsernamePasswordToken {
    private final RealmName realmName;

    public MyToken(String username, String password, boolean rememberMe, RealmName realmName) {
        super(username, password, rememberMe);
        this.realmName = realmName;
    }

    public RealmName getLoginType() {
        return realmName;
    }

}