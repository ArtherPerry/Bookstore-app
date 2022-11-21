package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dao.CustomerDao;
import com.example.bookstoreapp.ds.Book;
import com.example.bookstoreapp.ds.BookDto;
import com.example.bookstoreapp.ds.Customer;
import com.example.bookstoreapp.security.CustomUserDetailsService;
import com.example.bookstoreapp.service.CartService;
import com.example.bookstoreapp.service.CustomerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartService cartService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/slip")
    public String slip(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        model.addAttribute("customer",customerService.findCustomerByName(authentication.getName()));
        int id = customerService.findCustomerByName(authentication.getName()).getId();


        return "slip";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "login";
    }
    private List<Integer> bookQuantityList;

    @RequestMapping("/customer/register")
    public String register(Model model, BookDto bookDto){
        this.bookQuantityList=bookDto.getBookNumberList();
        //System.out.println("========================="+ bookQuantityList);
        model.addAttribute("customer",new Customer());
        return "register";
    }


    @PostMapping("/customer/save-customer")
    public String saveCustomer(Customer customer, BindingResult bindingResult){
        Set<BookDto> bookDtoSet=cartService.listCart();
        int index=0;
        for(BookDto bookDto:bookDtoSet){
            bookDto.setQuantity(this.bookQuantityList.get(index));
            index++;
        }

        if(bindingResult.hasErrors()){
            return "register";
        }
        checkCustomer(customer, bookDtoSet);
        cartService.clearCart();
        return "redirect:/login";
    }

    private void checkCustomer(Customer customer, Set<BookDto> bookDtoSet) {
        Customer existingCustomer= customerService.findCustomerByName(customer.getName());
        if(existingCustomer==null) {
            customerService.register(customer, bookDtoSet);
        }
        else{
            customerService.saveCustomerBookOrder(existingCustomer, bookDtoSet);
        }
    }

}
