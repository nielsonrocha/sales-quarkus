package com.github.nielsonrocha.sales.customer.repository;

import com.github.nielsonrocha.sales.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
