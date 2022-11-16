package com.example.bookstoreapp.dao;

import com.example.bookstoreapp.ds.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerDao  extends JpaRepository<Customer,Integer> {
    Optional<Customer> findCustomerByName(String name);
}
