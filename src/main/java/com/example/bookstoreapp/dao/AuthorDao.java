package com.example.bookstoreapp.dao;

import com.example.bookstoreapp.ds.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDao extends JpaRepository<Author,Integer> {
}

