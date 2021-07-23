package com.example.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;

@RestController
public class MyHealthIndicator implements HealthIndicator {

    private final AtomicReference<Health> health = new AtomicReference<>(Health.up().build());

    @Override
    public Health health() {
        return health.get();
    }

    @PutMapping("/monitoring/control/{control}")

    public Health controlHealth (@PathVariable("control") String control) {
        switch (control) {
            case "up":
                return up();
            case "down":
                return down();
            case "holiday":
                return holiday();
        }
        return null;
    }

    private Health up() {
        Health up = Health.up().build();
        this.health.set(up);
        return up;
    }

    private Health down() {
        Health down = Health.down().build();
        this.health.set(down);
        return down;
    }

    private Health holiday() {
        Health holiday = Health.status(new Status("HOLIDAY", "휴일")).build();
        this.health.set(holiday);
        return holiday;
    }
}