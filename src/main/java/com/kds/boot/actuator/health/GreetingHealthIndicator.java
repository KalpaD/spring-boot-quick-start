package com.kds.boot.actuator.health;

import com.kds.boot.service.GreetingService;
import com.kds.boot.web.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by KDS on 1/31/2017.
 */

/**
 * Demonstrate how to customize the spring boot actuator featurs to provide
 * customized HealthIndicator
 */
@Component
public class GreetingHealthIndicator implements HealthIndicator {

    @Autowired
    private GreetingService greetingService;

    @Override
    public Health health() {
        Collection<Greeting> greetings = greetingService.findAll();

        if (greetings == null || greetings.size() == 0) {
            return Health.down().withDetail("count", 0).build();
        }

        return Health.up().withDetail("count", greetings.size()).build();
    }
}
