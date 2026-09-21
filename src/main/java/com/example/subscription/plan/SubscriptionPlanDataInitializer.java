package com.example.subscription.plan;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
/**
 * Adds a few example plans when the database has no plans yet.
 */
public class SubscriptionPlanDataInitializer {

    @Bean
    /**
     * Runs once when the application starts and avoids adding duplicate examples.
     */
    CommandLineRunner seedSubscriptionPlans(SubscriptionPlanRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            repository.saveAll(List.of(
                    new SubscriptionPlan(
                            "Aarav Starter",
                            "Essential tools for individuals getting started.",
                            new BigDecimal("299.00"),
                            BillingCycle.MONTHLY,
                            true),
                    new SubscriptionPlan(
                            "Diya Growth",
                            "More capacity for growing teams and businesses.",
                            new BigDecimal("799.00"),
                            BillingCycle.MONTHLY,
                            true),
                    new SubscriptionPlan(
                            "Arjun Professional",
                            "Advanced features for professional users.",
                            new BigDecimal("1499.00"),
                            BillingCycle.MONTHLY,
                            true),
                    new SubscriptionPlan(
                            "Ananya Business",
                            "Collaboration and management tools for businesses.",
                            new BigDecimal("14999.00"),
                            BillingCycle.YEARLY,
                            true),
                    new SubscriptionPlan(
                            "Vivaan Enterprise",
                            "Flexible controls and priority support for enterprises.",
                            new BigDecimal("29999.00"),
                            BillingCycle.YEARLY,
                            true),
                    new SubscriptionPlan(
                            "Meera Legacy",
                            "A retained plan for existing customers.",
                            new BigDecimal("599.00"),
                            BillingCycle.MONTHLY,
                            false)
            ));
        };
    }
}
