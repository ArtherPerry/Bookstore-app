package com.example.bookstoreapp.dao;

import com.example.bookstoreapp.ds.CustomerBookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerBookOrderDao extends JpaRepository<CustomerBookOrder,Integer> {

    Optional<CustomerBookOrder> findCustomerBookOrdersByCustomer_Id(int CustomerId);

    Optional<CustomerBookOrder> findOrderIdByCustomer_Id(int id);

    Optional<CustomerBookOrder> findByCustomer_Id(int id);

}
