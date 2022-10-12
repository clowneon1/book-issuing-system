package com.clowneon1.bookissuingsystem.service;

import com.clowneon1.bookissuingsystem.model.Book;
import com.clowneon1.bookissuingsystem.model.UserSection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BookService {

    public List<Book> getAllBooks();
    public List<Book> getAllBooksByUserId(Long userId);
    public List<Book> getAllBooksBySectionId(Long userId);
    public Book getBookById(Long id);
    public Book createBookInUser(Long id,Book bookRequest);
    public Book createBookInSection(Long id, Book bookRequest);
    public Book updateBook(long id, @RequestBody Book bookRequest);
    public void deleteBook(long id);
    public void deleteAllBooksOfUser(Long userId);
    public void deleteAllBooksOfSection(Long sectionId);

    public Book sectionToUser(UserSection userSection);
    public Book userToSection(UserSection userSection);

}
