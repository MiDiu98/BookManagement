package com.ungmydieu.bookmanagement.controllerTest;

import com.ungmydieu.bookmanagement.models.dao.Book;
import com.ungmydieu.bookmanagement.models.dao.Comment;
import com.ungmydieu.bookmanagement.models.dao.Role;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.services.CommentService;
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
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    Comment comment1;
    Comment comment2;

    @BeforeEach
    public void init() {
        Role roleUser = new Role("ROLE_USER");
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleUser);
        User user = new User(1, "user1@gmail.com", "123", "firstname", "lastname", true, "avatar user", roles);
        User user2 = new User(2, "user2@gmail.com", "123", "firstname", "lastname", true, "avatar user", roles);
        Book book1 = new Book(1, "Book1", "author1", "description", LocalDateTime.now(), LocalDateTime.now(), "images", true, user);

        comment1 = new Comment(1, "comment1", LocalDateTime.now(), LocalDateTime.now(), book1, user);
        comment2 = new Comment(2, "comment2", LocalDateTime.now(), LocalDateTime.now(), book1, user2);
    }

    @Test
    public void test_getAllByBook() throws Exception{
        Mockito.when(commentService.getAllByBook(1)).thenReturn(Arrays.asList(comment1, comment2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/comments/1")
                .header("Authorization", "Bearer " + "$scope"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_getById() throws Exception{
        Mockito.when(commentService.getById(2)).thenReturn(comment2);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/comments/comment/2")
                .header("Authorization", "Bearer " + "$scope"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
