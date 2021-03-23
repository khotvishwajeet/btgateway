package com.bt.config;

import com.bt.exception.BadConfigurationException;
import com.bt.security.Authenticator;
import com.bt.security.SecurityAuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoutePostProcessor implements SmartLifecycle {

    private ApplicationContext context;

    private SecurityAuthProperties properties;

    private boolean started;

    @Autowired
    public RoutePostProcessor(ApplicationContext context, SecurityAuthProperties properties) {
        this.context = context;
        this.properties = properties;
    }

    @Override
    public void start() {
        started = true;
        properties.getRoutes().forEach((id, route) -> {
            log.debug("Processing route {}", id);
            Class[] classes = route.getAuthenticatorClasses();
            Authenticator[] authenticators = new Authenticator[classes.length];

            for (int i = 0; i < classes.length; i++) {
                Authenticator bean;
                try {
                    bean = (Authenticator) context.getAutowireCapableBeanFactory().createBean(classes[i]);
                } catch (BeansException e) {
                    throw new BadConfigurationException(
                            "Could not find bean of requested type for route " + id);
                }

                if (bean == null) {
                    log.error("Invalid configuration of route {}.", route.getId());
                    throw new BadConfigurationException(
                            "Invalid configuration. Authentication must be an instance of Authenticator.");
                }
                authenticators[i] = bean;
            }
            route.setAuthenticators(authenticators);
        });
    }

    @Override
    public void stop() {
        started = false;
    }

    @Override
    public boolean isRunning() {
        return started;
    }
}
