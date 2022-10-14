package com.clowneon1.bookissuingsystem.controller;

import com.clowneon1.bookissuingsystem.model.Category;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private CategoryServiceImp categoryServiceImp;

    @InjectMocks
    private CategoryController categoryController;

    Category c1 = new Category(1L, "c1",null);
    Category c2 = new Category(2L, "c2",null);
    Category c3 = new Category(3L, "c3",null);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void getAllCategories_success() throws Exception{
        List<Category> records = new ArrayList<>(Arrays.asList(c1,c2,c3));

        Mockito.when(categoryServiceImp.getAllCategories()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v3/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].categoryName", is(c2.getCategoryName())));
    }

    @Test
    public void getCategoryById() throws Exception{
        Mockito.when((categoryServiceImp.getCategoryById(c1.getId()))).thenReturn(c1);

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
                .id(4L)
                .categoryName("record")
                .books(null)
                .build();
        Mockito.when(categoryServiceImp.createCategory(record)).thenReturn(record);
        String content = objectWriter.writeValueAsString(record);
        System.out.println( "wdjakdjkajdka" + content);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v3/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.categoryName", is(record.getCategoryName())));
    }

    @Test
    public void updateCategory_success() throws Exception{
        Category updatedRecord = new Category(1L, "updated", null);
        Mockito.when(categoryServiceImp.createCategory(updatedRecord)).thenReturn(updatedRecord);

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
