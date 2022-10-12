package com.clowneon1.bookissuingsystem.repository;

import com.clowneon1.bookissuingsystem.model.Book;
import com.clowneon1.bookissuingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByUserId(Long userId);
    List<Book> findBySectionId(Long sectionId);
    @Transactional
    void deleteByUserId(Long userId);
    @Transactional
    void deleteBySectionId(Long sectionId);
}
