package com.clowneon1.bookissuingsystem.controller;

import com.clowneon1.bookissuingsystem.exception.ResourceNotFoundException;
import com.clowneon1.bookissuingsystem.model.Book;
import com.clowneon1.bookissuingsystem.model.Section;
import com.clowneon1.bookissuingsystem.model.User;
import com.clowneon1.bookissuingsystem.model.UserSection;
import com.clowneon1.bookissuingsystem.repository.BookRepository;
import com.clowneon1.bookissuingsystem.repository.UserRepository;
import com.clowneon1.bookissuingsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v3")
public class BookController {
    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Long userId){
        return new ResponseEntity<>(bookService.getBookById(userId), HttpStatus.OK);
    }

    //User stuff
    @GetMapping("/users/{id}/books")
    public ResponseEntity<List<Book>> getAllBooksByUserId(@PathVariable(value = "id") Long userId){
        return new ResponseEntity<>(bookService.getAllBooksByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/users/{id}/books")
    public ResponseEntity<Book> createBookInUser(@PathVariable(value = "id") Long userId,
                                                 @RequestBody Book bookRequest){
        return new ResponseEntity<>(bookService.createBookInUser(userId, bookRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}/books")
    public ResponseEntity<List<Book>> deleteAllBooksOfUser(@PathVariable(value = "id") Long userId) {
        bookService.deleteAllBooksOfUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /*------------------------------------------------------- */

    //Section stuff
    @GetMapping("/sections/{id}/books")
    public ResponseEntity<List<Book>> getAllBooksBySectionId(@PathVariable(value = "id") Long sectionId){
        return new ResponseEntity<>(bookService.getAllBooksBySectionId(sectionId), HttpStatus.OK);
    }

    @PostMapping("/sections/{id}/books")
    public ResponseEntity<Book> createBookInSection(@PathVariable(value = "id") Long sectionId,@RequestBody Book bookRequest){
        return new ResponseEntity<>(bookService.createBookInSection(sectionId, bookRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/sections/{id}/books")
    public ResponseEntity<List<Book>> deleteAllBooksOfSection(@PathVariable(value = "id") Long sectionId) {
        bookService.deleteAllBooksOfSection(sectionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-------------------------------------------------------*/

    //Section to user.
    @PutMapping("/section-to-user")
    public ResponseEntity<Book> sectionToUser(@RequestBody UserSection userSection){
        return new ResponseEntity<>(bookService.sectionToUser(userSection),HttpStatus.OK);
    }

    //User to section
    @PutMapping("/user-to-section")
    public ResponseEntity<Book> userToSection(@RequestBody UserSection userSection){
        return new ResponseEntity<>(bookService.userToSection(userSection),HttpStatus.OK);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book bookRequest) {
        return new ResponseEntity<>(bookService.updateBook(id,bookRequest), HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>("book deleted",HttpStatus.OK);
    }
    
}
