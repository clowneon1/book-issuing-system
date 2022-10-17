package com.clowneon1.bookissuingsystem.controller;

import com.clowneon1.bookissuingsystem.model.*;
import com.clowneon1.bookissuingsystem.service.BookService;
import com.clowneon1.bookissuingsystem.service.UserService;
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
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest {

    private MockMvc mockMvc;
    
    @Mock
    BookService bookService;
    
    @InjectMocks
    BookController bookController;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    Section s1 = Section.builder().id(1L).sectionName("section1").build();

    User u1 = User.builder().id(1L).fullName("yash").email("yash@gmail.com").phoneNo("+91 2323232323").build();

    Book b1 = Book.builder().id(1L).title("book1").description("dec1")
            .publishDate(new Date(2022-02-10)).section(s1).build();

    Book b2 = Book.builder().id(2L).title("book2").description("dec2")
            .publishDate(new Date(2022-02-10)).section(s1).build();

    Book b3 = Book.builder().id(1L).title("book1").description("dec1")
            .publishDate(new Date(2022-02-10)).section(s1).build();
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks() throws Exception{
        List<Book> records = new ArrayList<>(Arrays.asList(b1,b2,b3));

        when(bookService.getAllBooks()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v3/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", is(b1.getTitle())));
    }

    @Test
    public void getBookById() throws Exception {
        when(bookService.getBookById(anyLong())).thenReturn(b1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v3/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(b1.getTitle())))
                .equals(b1);

    }

    @Test
    public void getAllBooksByUserId() throws Exception {
        List<Book> records = new ArrayList<>(Arrays.asList(b1,b2,b3));
        when(bookService.getAllBooksByUserId(anyLong())).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v3/users/1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].title", is(b1.getTitle())));
    }

    @Test
    public void createBookInUser() throws Exception {
        Book record = Book.builder().id(1L).title("book1").description("dec1")
                .publishDate(new Date(2022-02-10)).user(u1).build();

        String content = objectWriter.writeValueAsString(record);

        when(bookService.createBookInUser(1L,record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v3/users/1/books")
                .characterEncoding(Charset.defaultCharset())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteAllBooksOfUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v3/users/1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .equals("All books of user deleted");

        verify(bookService, times(1)).deleteAllBooksOfUser(anyLong());
    }

    @Test
    public void getAllBooksBySectionId() throws Exception{
        List<Book> records = new ArrayList<>(Arrays.asList(b1,b2,b3));
        when(bookService.getAllBooksBySectionId(anyLong())).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v3/sections/1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].title", is(b1.getTitle())));
    }

    @Test
    public void createBookInSection() throws Exception {
        Book record = Book.builder().id(1L).title("book1").description("dec1")
                .publishDate(new Date(2022-02-10)).section(s1).build();

        String content = objectWriter.writeValueAsString(record);

        when(bookService.createBookInSection(1L,record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v3/sections/1/books")
                .characterEncoding(Charset.defaultCharset())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteAllBooksOfSection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v3/sections/1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .equals("All books of section deleted");

        verify(bookService, times(1)).deleteAllBooksOfSection(anyLong());
    }

    @Test
    public void sectionToUser() throws Exception{
        Book record = Book.builder().id(1L).title("book1").description("dec1")
                .publishDate(new Date(2022-02-10)).user(u1).issueDate(new Date(2022-02-01)).build();

        UserSection userSection = UserSection.builder().userId(1L).bookId(1L).sectionId(1L).build();

        when(bookService.sectionToUser(userSection)).thenReturn(record);

        String content = objectWriter.writeValueAsString(userSection);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v3/section-to-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    public void userToSection() throws Exception {
        Book record = Book.builder().id(1L).title("book1").description("dec1")
                .publishDate(new Date(2022-02-10)).user(u1).build();

        UserSection userSection = UserSection.builder().userId(1L).bookId(1L).sectionId(1L).build();

        when(bookService.userToSection(userSection)).thenReturn(record);

        String content = objectWriter.writeValueAsString(userSection);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v3/user-to-section")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    public void putCategory() throws Exception {
        Set<Category> categories = new HashSet<>();
        Category c1 = new Category(1L, "c1",null);
        Category c2 = new Category(2L, "c2",null);
        Category c3 = new Category(3L, "c3",null);
        categories.addAll(Arrays.asList(c1,c2,c3));

        Book record = Book.builder().id(1L).title("book1").description("dec1")
                .publishDate(new Date(2022-02-10)).section(s1).categories(categories).build();
        when(bookService.putCategory(1L,1L)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v3/books/1/categories/1")
                .characterEncoding(Charset.defaultCharset())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void updateBook() throws Exception {
        Book record = Book.builder().id(1L).title("book1").description("dec1")
                .publishDate(new Date(2022-02-10)).build();

        String content = objectWriter.writeValueAsString(record);

        when(bookService.updateBook(1L,record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v3/books/1")
                .characterEncoding(Charset.defaultCharset())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v3/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .equals("book deleted");

        verify(bookService, times(1)).deleteBook(anyLong());
    }
}