package com.clowneon1.bookissuingsystem.repository;

import com.clowneon1.bookissuingsystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
