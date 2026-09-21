package com.example.subscription.plan;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "subscription_plans")
/** JPA entity representing a purchasable subscription plan. */
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillingCycle billingCycle;

    @Column(nullable = false)
    private boolean active = true;

    protected SubscriptionPlan() {
    }

    public SubscriptionPlan(String name, String description, BigDecimal price,
                            BillingCycle billingCycle, boolean active) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.billingCycle = billingCycle;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BillingCycle getBillingCycle() { return billingCycle; }
    public void setBillingCycle(BillingCycle billingCycle) { this.billingCycle = billingCycle; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
