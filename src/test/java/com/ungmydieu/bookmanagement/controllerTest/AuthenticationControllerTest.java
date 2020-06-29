package com.ungmydieu.bookmanagement.controllerTest;

import com.google.gson.Gson;
import com.ungmydieu.bookmanagement.models.dao.Login;
import com.ungmydieu.bookmanagement.models.dao.Role;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.services.AuthenticationService;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {GsonAutoConfiguration.class})
public class AuthenticationControllerTest {
    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mockMvc;

    User user;

    @BeforeEach
    public void init() {
        Role roleUser = new Role("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        user = new User(1, "admin@email.com", "1234", "firstname", "lastname", true, "avatar", roles);
    }

    @AfterEach
    public void destroy() {}

    @Test
    public void test_login() throws Exception {
        String adminToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlbWFpbC5jb20iLCJzY29wZXMiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImlhdCI6MTU5MjgxNjI3NSwiZXhwIjoxNTk1NDA4Mjc1fQ.nrPyqpz-xVoVrYTjcEUtLf7NMbx9IJbkXnKxt-dZVew";
        Login login = new Login("admin@email.com", "1234");
        Gson gson = new Gson();
        String json = gson.toJson(login);
        Mockito.when(authenticationService.login(login)).thenReturn(adminToken);
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }
}
