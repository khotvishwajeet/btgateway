package com.bt.security.basic;

import com.bt.security.Credentials;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicAuthCredentials implements Credentials {
    private String user;

    private String password;

    private boolean authenticated;
}
