package com.bt.security;

import org.springframework.http.server.reactive.ServerHttpRequest;

public interface AuthenticationExtractor<T> {

    T extract(ServerHttpRequest request);
}
