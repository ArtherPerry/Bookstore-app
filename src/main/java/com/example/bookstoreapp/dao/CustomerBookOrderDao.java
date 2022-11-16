package com.example.bookstoreapp.dao;

import com.example.bookstoreapp.ds.CustomerBookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerBookOrderDao extends JpaRepository<CustomerBookOrder,Integer> {
}
