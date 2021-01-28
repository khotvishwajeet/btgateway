package com.bt.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@RefreshScope
@Configuration
@Component
@Getter
@Setter
public class ConfigurationService {
    @Value("${cats.auth.external.secret}")
    private String authSecret;
    @Value("${cats.auth.external.header}")
    private String authHeader;
}
