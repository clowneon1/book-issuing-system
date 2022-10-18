package com.clowneon1.bookissuingsystem.controller;

import com.clowneon1.bookissuingsystem.model.Book;
import com.clowneon1.bookissuingsystem.model.Category;
import com.clowneon1.bookissuingsystem.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v3")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category categoryRequest){
        return new ResponseEntity<>(categoryService.createCategory(categoryRequest),HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}/books")
    public ResponseEntity<Set<Book>> getBooksByCategoryId(@PathVariable long id){
        return new ResponseEntity<>(categoryService.getBooksByCategoryId(id),HttpStatus.OK);
    }


    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") long id){
        return new ResponseEntity<>(categoryService.getCategoryById(id),HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteAllCategories(@PathVariable("id") long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("category deleted",HttpStatus.OK);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody Category categoryRequest){
        return new ResponseEntity<>(categoryService.updateCategory(id, categoryRequest),HttpStatus.OK);
    }

    @DeleteMapping("/categories")
    public ResponseEntity<String> deleteAllCategories(){
        categoryService.deleteAllCategories();
        return new ResponseEntity<>("All categories deleted",HttpStatus.OK);
    }

}
