package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dao.CustomerBookOrderDao;
import com.example.bookstoreapp.dao.CustomerDao;
import com.example.bookstoreapp.dao.RolesDao;
import com.example.bookstoreapp.ds.BookDto;
import com.example.bookstoreapp.ds.Customer;
import com.example.bookstoreapp.ds.CustomerBookOrder;
import com.example.bookstoreapp.ds.Roles;
import com.example.bookstoreapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CartService cartService;
    @Autowired
    private RolesDao rolesDao;

    @Autowired
    private CustomerBookOrderDao customerBookOrderDao;

    public Customer findCustomerByName(String name){
        return customerDao.findCustomerByName(name).orElse(null);
    }

    @Transactional
    public void register(Customer customer, Set<BookDto> bookDtoList){
        Roles roles=rolesDao.findRolesByRoleName("ROLE_USER").get();
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.addRole(roles);
        for(BookDto bookDto:bookDtoList){
            customer.addBook(cartService.toEntity(bookDto));
        }
        Customer customer1=customerDao.saveAndFlush(customer);


        saveCustomerBookOrder(customer1,bookDtoList);

    }

    public void saveCustomerBookOrder(Customer customer1,Set<BookDto> bookDtoList) {
        CustomerBookOrder customerBookOrder=
                new CustomerBookOrder();
        customerBookOrder.setCustomer(customer1);
        customerBookOrder.setTotalAmount(totalPrices(bookDtoList));
        customerBookOrder.setOrderCode(generateCode(customer1));
        customerBookOrderDao.save(customerBookOrder);
    }

    private String generateCode(Customer customer){
        Random random=new Random();// 1 -> 99999
        int code= random.nextInt(100000) + 100000;
        return customer.getName() + code;
    }

    private double totalPrices(Set<BookDto> books){
        return  books
                .stream()
                .map(b -> b.getPrice() * b.getQuantity())
                .mapToDouble(d -> d)
                .sum();
    }


}
