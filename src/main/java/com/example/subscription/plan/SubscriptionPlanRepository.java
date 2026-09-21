package com.example.subscription.plan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/** Provides database access and name searching for subscription plans. */
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
    Page<SubscriptionPlan> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
