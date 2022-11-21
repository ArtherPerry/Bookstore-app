package com.example.bookstoreapp;

import com.example.bookstoreapp.dao.AccountDao;
import com.example.bookstoreapp.ds.Account;
import com.example.bookstoreapp.ds.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BookstoreAppApplication {

    @Autowired
    private AccountDao accountDao;

    public static void main(String[] args) {
        SpringApplication.run(BookstoreAppApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public ApplicationRunner runner(){
        return r ->{
            Account customerAccount = new Account("Thaw Thaw","TT",30000, AccountType.CASH);
            Account ownerAccount = new Account("John Doe","JD",5000,AccountType.CASH);
            accountDao.save(customerAccount);
            accountDao.save(ownerAccount);
        };
    }

}
