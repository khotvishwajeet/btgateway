package com.bt.config;

import com.bt.security.SecurityAuthProperties;
import com.bt.security.basic.BasicAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityBasicAuthConfiguration {

    private SecurityAuthProperties properties;

    @Autowired
    public SecurityBasicAuthConfiguration(SecurityAuthProperties properties) {
        this.properties = properties;
    }

    @Bean
    public BasicAuthProvider getBasicAuthProvider() {
        return new BasicAuthProvider(properties.getUser(), properties.getPassword());
    }
}
