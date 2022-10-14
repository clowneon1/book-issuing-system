package com.clowneon1.bookissuingsystem.service;

import com.clowneon1.bookissuingsystem.model.Book;
import com.clowneon1.bookissuingsystem.model.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    public List<Category> getAllCategories();
    public Category getCategoryById(long categoryId);
    public void deleteCategory(long categoryId);
    public void deleteAllCategories();

    public Category createCategory(Category category);

    Set<Book> getBooksByCategoryId(long id);

    Category updateCategory(long id, Category categoryRequest);
}
