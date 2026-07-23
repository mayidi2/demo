package org.xian1.customerservice.controller;

import org.springframework.web.bind.annotation.*;
import org.xian1.customerservice.model.Customer;
import org.xian1.customerservice.service.CustomerService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public Customer register(@RequestBody Map<String, String> payload) {
        return customerService.register(
                payload.get("username"),
                payload.get("password"),
                payload.get("fullName")
        );
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> payload) {
        boolean ok = customerService.login(payload.get("username"), payload.get("password"));
        return Map.of("authenticated", ok);
    }

    @PostMapping("/validate")
    public Map<String, Object> validate(@RequestBody Map<String, String> payload) {
        boolean ok = customerService.login(payload.get("username"), payload.get("password"));
        return Map.of("valid", ok);
    }

    @GetMapping("/users/{username}")
    public Customer getUser(@PathVariable String username) {
        return customerService.findByUsername(username).orElse(null);
    }

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        return Map.of("totalUsers", customerService.totalUsers());
    }
}