package com.bt.security.basic;

import com.bt.exception.AuthenticationException;
import lombok.Getter;

@Getter
public class BasicAuthProvider {
    private String user;

    private String password;

    public BasicAuthProvider(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void authenticate(String user, String password) {
        if (!this.user.equalsIgnoreCase(user)) {
            throw new AuthenticationException("Invalid user " + user);
        }

        if (!this.password.equals(password)) {
            throw new AuthenticationException("Invalid password!");
        }
    }
}
