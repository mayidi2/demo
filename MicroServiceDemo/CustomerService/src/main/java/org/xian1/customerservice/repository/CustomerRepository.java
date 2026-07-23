package org.xian1.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xian1.customerservice.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByUsername(String username);
}