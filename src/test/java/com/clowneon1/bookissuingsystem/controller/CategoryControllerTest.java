package com.clowneon1.bookissuingsystem.controller;

import com.clowneon1.bookissuingsystem.model.Book;
import com.clowneon1.bookissuingsystem.model.Category;
import com.clowneon1.bookissuingsystem.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CategoryControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    Category c1 = new Category(1L, "c1",null);
    Category c2 = new Category(2L, "c2",null);
    Category c3 = new Category(3L, "c3",null);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void getAllCategories() throws Exception{
        List<Category> records = new ArrayList<>(Arrays.asList(c1,c2,c3));

        when(categoryService.getAllCategories()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v3/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].categoryName", is(c2.getCategoryName())));
    }

    @Test
    public void getCategoryById() throws Exception{
        when((categoryService.getCategoryById(c1.getId()))).thenReturn(c1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v3/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.categoryName", is(c1.getCategoryName())));
    }

    @Test
    public void createCategory() throws Exception{
        Category record = Category.builder()
                .id(1L)
                .categoryName("record")
                .build();
        doReturn(record).when(categoryService).createCategory(any());
        String content = objectMapper.writeValueAsString(record);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v3/categories")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.categoryName", is(record.getCategoryName())));
    }

    @Test
    public void updateCategory() throws Exception{
        Category updatedRecord = new Category(1L, "updated", null);
        when(categoryService.createCategory(updatedRecord)).thenReturn(updatedRecord);

        doReturn(updatedRecord).when(categoryService).updateCategory(anyLong(),any());
        String content = objectMapper.writeValueAsString(updatedRecord);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v3/categories/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.categoryName", is(updatedRecord.getCategoryName())));
    }

    @Test
    public void deleteCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v3/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$",is("category deleted")));

        verify(categoryService, times(1)).deleteCategory(anyLong());
    }

    @Test
    public void getBooksByCategoryId() throws Exception {
        Book b1 = Book.builder().id(1L).title("book1").description("dec1")
                .publishDate(LocalDate.of(2022, Month.APRIL,01)).build();

        Set<Book> set = new HashSet<>();
        set.add(b1);
        c1.setBooks(set);

        doReturn(c1.getBooks()).when(categoryService).getBooksByCategoryId(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v3/categories/1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andDo(print())
                .andExpect(jsonPath("$[0].title", is(b1.getTitle())));
    }

    @Test
    public void deleteAllCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v3/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$",is("All categories deleted")));

        verify(categoryService, times(1)).deleteAllCategories();
    }

}
