package com.example.subscription.plan;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
/** Signals that a requested subscription plan does not exist. */
public class PlanNotFoundException extends RuntimeException {
    public PlanNotFoundException(Long id) {
        super("Subscription plan not found: " + id);
    }
}
