package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dao.AuthorDao;
import com.example.bookstoreapp.dao.BookDao;
import com.example.bookstoreapp.dao.CategoryDao;
import com.example.bookstoreapp.ds.Author;
import com.example.bookstoreapp.ds.Book;
import com.example.bookstoreapp.ds.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private CategoryDao categoryDao;

    public void saveCategory(Category category) {
        categoryDao.save(category);
    }

    public List<Category> findAllCategory() {
        return categoryDao.findAll();
    }

    public Category findCategoryById(int id) {
        return categoryDao.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void saveAuthor(Author author) {
        authorDao.save(author);
    }

    public List<Author> findAllAuthor() {
        return authorDao.findAll();
    }

    @Transactional
    public void saveBook(Book book, int categoryId, int authorId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(EntityNotFoundException::new);
        Author author = authorDao.findById(authorId).orElseThrow(EntityNotFoundException::new);

        category.addBook(book);
        author.addBook(book);

        bookDao.save(book);
    }

    public List<Book> findAllBook() {
        return bookDao.findAll();
    }

    public Book findBookById(int id) {
        return bookDao.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
