package com.clowneon1.bookissuingsystem.service;

import com.clowneon1.bookissuingsystem.exception.ResourceNotFoundException;
import com.clowneon1.bookissuingsystem.model.Book;
import com.clowneon1.bookissuingsystem.model.Category;
import com.clowneon1.bookissuingsystem.model.UserSection;
import com.clowneon1.bookissuingsystem.repository.BookRepository;
import com.clowneon1.bookissuingsystem.repository.CategoryRepository;
import com.clowneon1.bookissuingsystem.repository.SectionRepository;
import com.clowneon1.bookissuingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImp implements BookService{

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private CategoryRepository categoryRepository;

//    public BookServiceImp(BookRepository bookRepository, UserRepository userRepository, SectionRepository sectionRepository, CategoryRepository categoryRepository) {
//        this.bookRepository = bookRepository;
//        this.userRepository = userRepository;
//        this.sectionRepository = sectionRepository;
//        this.categoryRepository = categoryRepository;
//    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAllBooksByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Not found User with id = " + userId);
        }
        return bookRepository.findByUserId(userId);
    }

    @Override
    public List<Book> getAllBooksBySectionId(Long sectionId) {
        if(!sectionRepository.existsById(sectionId)){
            throw new ResourceNotFoundException("Not found section with id = " + sectionId);
        }
        return bookRepository.findBySectionId(sectionId);
    }

    @Override
    public Book getBookById(Long id) {
        return  bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));
    }

    @Override
    public Book createBookInUser(Long id, Book bookRequest) {
//        Book _book = bookRepository.save(bookRequest);
//        return mapUserToBook(_book.getId(),id);
        Book book = userRepository.findById(id).map(user -> {
            bookRequest.setUser(user);
            return bookRepository.save(bookRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + id));
        return bookRepository.save(book);
    }

    @Override
    public Book createBookInSection(Long id, Book bookRequest) {
        Book book = sectionRepository.findById(id).map(section -> {
            bookRequest.setSection(section);
            return bookRepository.save(bookRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found section with id = " + id));
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(long id, Book bookRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookId " + id + "not found"));

        book.setTitle(bookRequest.getTitle());
        book.setDescription(bookRequest.getDescription());
        book.setPublishDate(bookRequest.getPublishDate());
        book.setIssueDate(bookRequest.getIssueDate());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteAllBooksOfUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Not found User with id = " + userId);
        }
        bookRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteAllBooksOfSection(Long sectionId) {
        if(!sectionRepository.existsById(sectionId)){
            throw new ResourceNotFoundException("Not found section with id = " + sectionId);
        }
        bookRepository.deleteBySectionId(sectionId);
    }

    @Override
    public Book sectionToUser(UserSection userSection) {

        if(!sectionRepository.existsById(userSection.getSectionId())){
            throw new ResourceNotFoundException("Not found section with id = " + userSection.getSectionId());
        }

        Book book = bookRepository.findById(userSection.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + userSection.getBookId()));
        book.setSection(null);
        book.setIssueDate(userSection.getIssueDate());
        Book _book = userRepository.findById(userSection.getUserId()).map(user -> {
            book.setUser(user);
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + userSection.getUserId()));

        return bookRepository.save(book);
    }

    @Override
    public Book userToSection(UserSection userSection) {
        if(!userRepository.existsById(userSection.getUserId())){
            throw new ResourceNotFoundException("Not found user with id = " + userSection.getUserId());
        }

        Book book = bookRepository.findById(userSection.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + userSection.getBookId()));
        book.setUser(null);
        book.setIssueDate(userSection.getIssueDate());
        Book _book = sectionRepository.findById(userSection.getSectionId()).map(section -> {
            book.setSection(section);
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Section with id = " + userSection.getSectionId()));
        return bookRepository.save(book);
    }

    @Override
    public Book putCategory(long bid, long cid) {
        Set<Category> categorySet = null;
        Book book = bookRepository.findById(bid)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bid));
        Category category = categoryRepository.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Not found category with id = " + cid));
        categorySet = book.getCategories();
        categorySet.add(category);
        book.setCategories(categorySet);
        return bookRepository.save(book);
    }

    private Book mapUserToBook(Long bookId, long userId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));
        Book _book = userRepository.findById(userId).map(user -> {
            book.setUser(user);
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + userId));

        return bookRepository.save(book);
    }
}
