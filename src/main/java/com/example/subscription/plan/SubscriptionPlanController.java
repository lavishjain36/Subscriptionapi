package com.example.subscription.plan;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plans")
@SecurityRequirement(name = "bearerAuth")
/** Provides authenticated CRUD endpoints for subscription plans. */
public class SubscriptionPlanController {

    private final SubscriptionPlanRepository repository;

    public SubscriptionPlanController(SubscriptionPlanRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Page<SubscriptionPlan> list(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);
        PageRequest pageable = PageRequest.of(safePage, safeSize, Sort.by("name").ascending());
        return name.isBlank()
                ? repository.findAll(pageable)
                : repository.findByNameContainingIgnoreCase(name.trim(), pageable);
    }

    @GetMapping("/{id}")
    public SubscriptionPlan get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionPlan create(@RequestBody SubscriptionPlan plan) {
        return repository.save(plan);
    }

    @PutMapping("/{id}")
    public SubscriptionPlan update(@PathVariable Long id, @RequestBody SubscriptionPlan input) {
        SubscriptionPlan plan = get(id);
        plan.setName(input.getName());
        plan.setDescription(input.getDescription());
        plan.setPrice(input.getPrice());
        plan.setBillingCycle(input.getBillingCycle());
        plan.setActive(input.isActive());
        return repository.save(plan);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repository.delete(get(id));
    }
}
