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

    @BeforeEach
    public void init() {
        Role roleUser = new Role("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        User user = new User(1, "user@gmail.com", "123", "firstname", "lastname", true, "avatar", roles);
    }

    @AfterEach
    public void destroy() {}

    @Test
    public void test_login() throws Exception {
        Login login = new Login("admin@email.com", "1234");
        Gson gson = new Gson();
        String json = gson.toJson(login);
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }
}
