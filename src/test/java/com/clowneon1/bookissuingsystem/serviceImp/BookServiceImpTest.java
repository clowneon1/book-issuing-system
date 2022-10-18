package com.clowneon1.bookissuingsystem.serviceImp;

import com.clowneon1.bookissuingsystem.model.*;
import com.clowneon1.bookissuingsystem.repository.BookRepository;
import com.clowneon1.bookissuingsystem.repository.CategoryRepository;
import com.clowneon1.bookissuingsystem.repository.SectionRepository;
import com.clowneon1.bookissuingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookServiceImpTest {

    private MockMvc mockMvc;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookServiceImp bookServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookServiceImp).build();

    }

    Section s1 = Section.builder().id(1L).sectionName("section1").build();

    User u1 = User.builder().id(1L).fullName("yash").email("yash@gmail.com").phoneNo("+91 2323232323").build();

    Book b1 = Book.builder().id(1L).title("book1").description("dec1")
            .publishDate(LocalDate.of(2022, Month.APRIL,01)).section(s1).build();

    Book b2 = Book.builder().id(2L).title("book2").description("dec2")
            .publishDate(LocalDate.of(2022,Month.APRIL,01)).section(s1).build();

    Book b3 = Book.builder().id(1L).title("book1").description("dec1")
            .publishDate(LocalDate.of(2022,Month.APRIL,01)).section(s1).build();

    @Test
    public void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(b1,b2,b3));
        assertThat(bookServiceImp.getAllBooks()).isNotNull();
        assertThat(bookServiceImp.getAllBooks().size()).isEqualTo(3);
    }

    @Test
    public void getAllBooksByUserId() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findByUserId(anyLong())).thenReturn(Arrays.asList(b1,b2,b3));

        assertThat(bookServiceImp.getAllBooksByUserId(anyLong())).isNotNull();
        assertThat(bookServiceImp.getAllBooksByUserId(anyLong()).size()).isEqualTo(3);
    }

    @Test
    public void getAllBooksBySectionId() {
        long id = 1;
        when(sectionRepository.existsById(id)).thenReturn(true);
        when(bookRepository.findBySectionId(id)).thenReturn(Arrays.asList(b1,b2,b3));

        assertThat(bookServiceImp.getAllBooksBySectionId(id)).isNotNull();
        assertThat(bookServiceImp.getAllBooksBySectionId(id).size()).isEqualTo(3);
    }

    @Test
    public void getBookById() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(b1));
        assertThat(bookServiceImp.getBookById(anyLong())).isNotNull().isEqualTo(b1);
    }

    @Test
    public void createBookInUser() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(u1));
        when(bookRepository.save(b1)).thenReturn(b1);
        assertThat(bookServiceImp.createBookInUser(anyLong(),b1)).isNotNull();
        verify(bookRepository, times(2)).save(any(Book.class));
    }

    @Test
    public void createBookInSection() {
        Book record = Book.builder().id(2L).title("book2").description("dec2")
                .publishDate(LocalDate.of(2022,Month.APRIL,01)).section(s1).build();

        when(sectionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(s1));
        when(bookRepository.save(record)).thenReturn(record);
        assertThat(bookServiceImp.createBookInSection(anyLong(),record)).isNotNull();
        verify(bookRepository, times(2)).save(any(Book.class));
    }

    @Test
    public void updateBook() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(b1));
        when(bookRepository.save(any(Book.class))).thenReturn(b1);
        assertThat(bookServiceImp.updateBook(anyLong(),b1)).isNotNull();
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void deleteBook() {
        bookServiceImp.deleteBook(anyLong());
        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteAllBooksOfUser() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        bookServiceImp.deleteAllBooksOfUser(anyLong());
        verify(bookRepository, times(1)).deleteByUserId(anyLong());
    }

    @Test
    public void deleteAllBooksOfSection() {
        when(sectionRepository.existsById(anyLong())).thenReturn(true);
        bookServiceImp.deleteAllBooksOfSection(anyLong());
        verify(bookRepository, times(1)).deleteBySectionId(anyLong());
    }

    @Test
    public void sectionToUser() {
        Book book = Book.builder().id(1L).user(u1).title("book1").description("description1")
                .publishDate(LocalDate.of(2022,Month.APRIL,01)).issueDate(LocalDate.of(2022,Month.APRIL,01)).build();
        UserSection userSection = UserSection.builder().userId(1L).sectionId(1L).bookId(1L)
                .issueDate(LocalDate.of(2022,Month.APRIL,01)).build();

        when(sectionRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(u1));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        assertThat(bookServiceImp.sectionToUser(userSection)).isNotNull().isEqualTo(book);
        verify(bookRepository, times(2)).save(any(Book.class));

    }

    @Test
    public void userToSection() {
        Book book = Book.builder().id(1L).user(u1).title("book1").description("description1")
                .publishDate(LocalDate.of(2022,Month.APRIL,01)).issueDate(LocalDate.of(2022,Month.APRIL,01)).build();
        UserSection userSection = UserSection.builder().userId(1L).sectionId(1L).bookId(1L)
                .issueDate(null).build();

        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(sectionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(s1));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        book.setIssueDate(userSection.getIssueDate());

        assertThat(bookServiceImp.userToSection(userSection)).isNotNull().isEqualTo(book);
        verify(bookRepository, times(2)).save(any(Book.class));

    }

    @Test
    public void putCategory() {
        Category category = Category.builder().id(1L).categoryName("cat1").build();
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(category);
        Book book = Book.builder().id(1L).user(u1).title("book1").description("description1")
                .publishDate(LocalDate.of(2022,Month.APRIL,01)).issueDate(LocalDate.of(2022,Month.APRIL,01)).categories(categorySet).build();


        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(category));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        assertThat(bookServiceImp.putCategory(1L,1L)).isNotNull().isEqualTo(book);
    }
}