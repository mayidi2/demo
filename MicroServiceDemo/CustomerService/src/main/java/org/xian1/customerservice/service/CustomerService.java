package org.xian1.customerservice.service;

import org.springframework.stereotype.Service;
import org.xian1.customerservice.model.Customer;
import org.xian1.customerservice.repository.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer register(String username, String password, String fullName) {
        if (customerRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        Customer customer = new Customer(username, password, fullName);
        return customerRepository.save(customer);
    }

    public boolean login(String username, String password) {
        return customerRepository.findByUsername(username)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public long totalUsers() {
        return customerRepository.count();
    }
}