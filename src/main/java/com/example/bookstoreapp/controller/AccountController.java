package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dao.AccountDao;
import com.example.bookstoreapp.ds.Account;
import com.example.bookstoreapp.ds.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;


@RestController
@RequestMapping("/payment")
public class AccountController {

    @Autowired
    private AccountDao accountDao;

    //curl -X POST -H "Content-Type: application/json" -d '{"name":"Thuza Nwe","totalAmount": 25.5,"accountNumber": "ThuzaNwe103085"}' http://localhost:9000/payment/checkout
    @Transactional
    @PostMapping(value = "/checkout",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkOut(@RequestBody AccountInfo accountInfo){
        Optional<Account> accountOptional = accountDao.findAccountByNameAndAccountNumber(accountInfo.getName(),accountInfo.getAccountNumber());
        if(!accountOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Account ownerAccount = accountDao.findById(1).get();
        Account userAccount = accountOptional.get();

        if(accountInfo.getTotalAmount() > userAccount.getAmount()){
            return ResponseEntity.badRequest().body("Insufficient Amount");
        }
        else {
            transferAmount(accountInfo.getTotalAmount(), ownerAccount, userAccount);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }



    public double toDouble(String amount){
        return Double.parseDouble(amount);
    }

    //curl -X POST -H "Content-Type: application/json" -d '{"name":"Thuza Nwe","amount":50000,"accountType":"CASH"}' localhost:9000/payment/register
    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity register(@RequestBody Account account){
        account.setAccountNumber(generateId(account.getName()));
        return ResponseEntity.ok(accountDao.save(account));
    }


    public String generateId(String name){
        return removeWhiteSpace(name)+(new Random().nextInt(10000)+ 100000);
    }

    private static String removeWhiteSpace(String name) {
        String n1 = "";
        if(name.contains(" ")){
            String[] names = name.split( " ");

            for (String s: names){
                n1+=s;
            }
        }else {
            n1= name;
        }
        return n1.trim();
    }

    private void transferAmount(double totalAmount, Account ownerAccount, Account userAccount) {
        userAccount.setAmount(userAccount.getAmount()- totalAmount);
        ownerAccount.setAmount(ownerAccount.getAmount() + totalAmount);
    }


}
