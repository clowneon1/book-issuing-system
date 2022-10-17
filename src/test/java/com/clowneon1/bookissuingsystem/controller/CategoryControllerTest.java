package com.clowneon1.bookissuingsystem.controller;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
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
    public void getAllCategories_success() throws Exception{
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
    public void createCategory_success() throws Exception{
        Category record = Category.builder()
                .id(1L)
                .categoryName("record")
                .build();

        //String str = "{\"categoryName\":\"record\"}";
        String content = objectWriter.writeValueAsString(record);
        when(categoryService.createCategory(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v3/categories")
                .characterEncoding(Charset.defaultCharset())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

//        String str = mockMvc.perform(mockRequest).andReturn().getRequest().getContentAsString();
//        System.out.println("lklklklklklkl" + str);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.categoryName", is(record.getCategoryName())));
    }

    @Test
    public void updateCategory_success() throws Exception{
        Category updatedRecord = new Category(1L, "updated", null);
        when(categoryService.createCategory(updatedRecord)).thenReturn(updatedRecord);

        String updatedContent = objectWriter.writeValueAsString(updatedRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v3/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.categoryName", is(updatedRecord.getCategoryName())));
    }
}
