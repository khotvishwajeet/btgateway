package com.bt.security.oauth2;

import com.bt.security.Credentials;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.jwt.Jwt;

@Getter
@Setter
public class JwtCredentials implements Credentials {

    private Jwt jwt;
}
