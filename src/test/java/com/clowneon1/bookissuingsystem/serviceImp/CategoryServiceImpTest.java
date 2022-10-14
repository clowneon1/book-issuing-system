
package com.clowneon1.bookissuingsystem.serviceImp;
import com.clowneon1.bookissuingsystem.controller.CategoryController;
import com.clowneon1.bookissuingsystem.model.Book;
import com.clowneon1.bookissuingsystem.model.Category;
import com.clowneon1.bookissuingsystem.repository.CategoryRepository;
import com.clowneon1.bookissuingsystem.serviceImp.CategoryServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImpTest {

    private MockMvc mockMvc;

    Category c1 = new Category(1L, "c1",null);
    Category c2 = new Category(2L, "c2",null);
    Category c3 = new Category(3L, "c3",null);

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImp categoryServiceImp;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryServiceImp).build();
    }

    @Test
    public void getAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(c1,c2,c3));
        assertThat(categoryServiceImp.getAllCategories().size()).isEqualTo(3);
    }

    @Test
    public void getCategoryById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(c1));
        assertThat(categoryServiceImp.getCategoryById(anyLong())).isNotNull();
    }

    @Test
    public void deleteCategory() {
        categoryServiceImp.deleteCategory(anyLong());
        verify(categoryRepository, times(1)).deleteById(anyLong());

    }

    @Test
    public void deleteAllCategories() {
        categoryServiceImp.deleteAllCategories();
        verify(categoryRepository,times(1)).deleteAll();
    }

    @Test
    public void createCategory() {
        Category record = new Category(4L,"c4", null);
        when(categoryRepository.save(record)).thenReturn(record);
        assertThat(categoryServiceImp.createCategory(record)).isNotNull();
    }

    @Test
    public void getBooksByCategoryId() {
        long id = 1;
        when(categoryRepository.findById(id)).thenReturn(Optional.ofNullable(c1));
        categoryServiceImp.getBooksByCategoryId(id);
        verify(categoryRepository,times(1)).findById(id);
    }

    @Test
    public void updateCategory() {
        Category updatedCategory = Category.builder()
                .categoryName("updated")
                .build();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(c1));
        Category _getRecord = categoryServiceImp.getCategoryById(anyLong());

        when(categoryRepository.save(_getRecord)).thenReturn(_getRecord);
        _getRecord.setCategoryName(updatedCategory.getCategoryName());
        assertThat(categoryServiceImp.updateCategory(anyLong(),updatedCategory)).isNotNull();

    }
}