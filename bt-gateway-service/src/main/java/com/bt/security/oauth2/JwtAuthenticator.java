package com.bt.security.oauth2;

import com.bt.exception.AuthenticationException;
import com.bt.security.Authenticator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

@Slf4j
public class JwtAuthenticator implements Authenticator<JwtCredentials> {

    private JwtExtractor extractor;

    private JwtDecoder decoder;

    @Autowired
    public JwtAuthenticator(JwtExtractor extractor, JwtDecoder decoder) {
        this.extractor = extractor;
        this.decoder = decoder;
    }

    @Override
    public JwtCredentials authenticate(ServerHttpRequest request) {
        String token = extractor.extract(request);

        try {
            Jwt jwt = decoder.decode(token);
            JwtCredentials credentials = new JwtCredentials();
            credentials.setJwt(jwt);

            return credentials;
        } catch (JwtException e) {
            log.error(e.getMessage());
            throw new AuthenticationException("Invalid token!");
        }
    }
}
