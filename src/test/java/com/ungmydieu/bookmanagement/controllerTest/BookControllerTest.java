package com.ungmydieu.bookmanagement.controllerTest;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dao.Role;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.BookDTO;
import com.ungmydieu.bookmanagement.models.dto.BookPage;
import com.ungmydieu.bookmanagement.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {GsonAutoConfiguration.class})
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Converter<Book, BookDTO> bookBookDTOConverter;

    @MockBean
    private BookService bookService;

    String adminToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlbWFpbC5jb20iLCJzY29wZXMiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImlhdCI6MTU5MjgxNjI3NSwiZXhwIjoxNTk1NDA4Mjc1fQ.nrPyqpz-xVoVrYTjcEUtLf7NMbx9IJbkXnKxt-dZVew";

    Book book1;
    Book book2;
    Book book3;

    @BeforeEach
    public void init() {
        Role roleUser = new Role("ROLE_USER");
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleUser);
        User user = new User(1, "user1@gmail.com", "123", "firstname", "lastname", true, "avatar user", roles);
        book1 = new Book(1, "Book1", "author1", "description", LocalDateTime.now(), LocalDateTime.now(), "images", true, user);
        book2 = new Book(2, "Book2", "author2", "description", LocalDateTime.now(), LocalDateTime.now(), "images", true, user);
        book3 = new Book(3, "Book3", "author3", "description", LocalDateTime.now(), LocalDateTime.now(), "images", false, user);
    }

    @Test
    public void test_getEnabledBooks() throws Exception{
        Mockito.when(bookService.getAllBooksEnable(0, 5, "id", "asc")).thenReturn(new BookPage(bookBookDTOConverter.convert(Arrays.asList(book1, book2)), 0, 1));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/enable?pageNo=0&pageSize=5&sortBy=id&order=asc")
                .header("Authorization", "Bearer " + "$scope"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_getAllBook() throws Exception{
        BookPage bookPage = new BookPage();
        bookPage.setBooksDto(bookBookDTOConverter.convert(Arrays.asList(book1, book2, book3)));
        bookPage.setCurrentPage(0);
        bookPage.setTotalPages(1);

        Mockito.when(bookService.getAllBooks(0, 10, "id", "asc")).thenReturn(bookPage);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/books")
                .param("enabled", "false")
                .param("pageNo", "0")
                .param("pageSize", "10")
                .param("sortBy", "id")
                .param("order", "asc")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_getBookByAdmin() throws Exception{
        BookPage bookPage = new BookPage();
        bookPage.setBooksDto(bookBookDTOConverter.convert(Arrays.asList(book3)));
        bookPage.setCurrentPage(0);
        bookPage.setTotalPages(1);

        Mockito.when(bookService.getBooksByAdmin(false, 0, 10, "id", "asc")).thenReturn(bookPage);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/books/status")
                .param("enabled", "false")
                .param("pageNo", "0")
                .param("pageSize", "10")
                .param("sortBy", "id")
                .param("order", "asc")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_getBookById() throws Exception{
        Mockito.when(bookService.getBookByIdAndEnabledTrue(1)).thenReturn(book1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1")
                .header("Authorization", "Bearer " + "$scope"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_getBookByIdAdmin() throws Exception{
        Mockito.when(bookService.getBookById(3)).thenReturn(book3);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/books/3")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_findByTitleOrAuthor() throws Exception{
        Mockito.when(bookService.findByTitleAndAuthor("book1", "author1")).thenReturn(Arrays.asList(book1));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/find?title=book1&author=author1")
                .header("Authorization", "Bearer " + "$scope"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_findByUser() throws Exception{
        Mockito.when(bookService.findByUser(1)).thenReturn(Arrays.asList(book1, book2, book3));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/find/user?userId=1")
                .header("Authorization", "Bearer " + "$scope"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
