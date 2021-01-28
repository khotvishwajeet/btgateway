package com.bt.filter;

import com.bt.configuration.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Autowired
    private ConfigurationService configurationService;

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //Custom Pre Filter. Suppose we can extract JWT and perform Authentication
        return (exchange, chain) -> {
            printLine();
            logger.info("First pre filter" + exchange.getRequest());
            printLine();
            //Custom Post Filter.Suppose we can call error response handler based on error code.
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                ServerHttpRequest request = exchange.getRequest();
                printLine();

                logger.info("First post filter" + exchange.getRequest());
                logger.info(String.format("%s %s %s %s", request.getRemoteAddress(),
                        response.getStatusCode(),
                        request.getMethod(),
                        request.getURI().toString()));

                printLine();
            }));
        };
    }

    public void printLine() {
        logger.info("---------------------------------------------------------");
    }


    public static class Config {

    }
}