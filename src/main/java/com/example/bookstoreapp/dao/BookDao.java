package com.example.bookstoreapp.dao;

import com.example.bookstoreapp.ds.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<Book,Integer> {
}