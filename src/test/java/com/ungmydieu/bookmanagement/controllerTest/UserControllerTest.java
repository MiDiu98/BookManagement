package com.ungmydieu.bookmanagement.controllerTest;

import com.google.gson.Gson;
import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.Role;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.models.dto.UserPage;
import com.ungmydieu.bookmanagement.services.UserService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {GsonAutoConfiguration.class})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Converter<User, UserDTO> userUserDTOConverter;

    @MockBean
    private UserService userService;

    private User user1;
    private User user2;
    private User admin;

    String adminToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlbWFpbC5jb20iLCJzY29wZXMiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImlhdCI6MTU5MjgxNjI3NSwiZXhwIjoxNTk1NDA4Mjc1fQ.nrPyqpz-xVoVrYTjcEUtLf7NMbx9IJbkXnKxt-dZVew";

    @BeforeEach
    public void init() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        user1 = new User(2, "user1@gmail.com", "123", "firstname", "lastname", true, "avatar user", roles);
        user2 = new User(3, "user2@gmail.com", "123", "firstname", "lastname", false, "avatar user", roles);
        roles.add(roleAdmin);
        admin = new User(1, "admin@gmail.com", "123", "firstname", "lastname", true, "avatar admin", roles);
    }

    @Test
    public void getAllUser() throws Exception {
        UserPage userPage = new UserPage();
        userPage.setUsersDto(userUserDTOConverter.convert(Arrays.asList(admin, user1, user2)));
        userPage.setCurrentPage(0);
        userPage.setTotalPages(1);

        Mockito.when(userService.getAllUsers(0, 10,"id", "asc")).thenReturn(userPage);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users")
                .param("sortBy", "id")
                .param("order", "asc")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getDisableUser_isOK() throws Exception {
        UserPage userPage = new UserPage();
        userPage.setUsersDto(userUserDTOConverter.convert(Arrays.asList(user2)));
        userPage.setCurrentPage(0);
        userPage.setTotalPages(1);

        Mockito.when(userService.getUserByAdmin(false, 0, 10,"id", "asc")).thenReturn(userPage);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/status")
                .param("enabled", "false")
                .param("sortBy", "id")
                .param("order", "asc")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getEnableUser_isOK() throws Exception {
        UserPage userPage = new UserPage();
        userPage.setUsersDto(userUserDTOConverter.convert(Arrays.asList(admin, user1)));
        userPage.setCurrentPage(0);
        userPage.setTotalPages(1);

        Mockito.when(userService.getUserByAdmin(true, 0, 10, "id", "asc")).thenReturn(userPage);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/status")
                            .param("enabled", "true")
                            .param("pageNo", "0")
                            .param("pageSize", "10")
                            .param("sortBy", "id")
                            .param("order", "asc")
                            .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getUserById_isOK() throws Exception {
        Mockito.when(userService.getUserById(1)).thenReturn(user1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1" )
                .header("Authorization", "Bearer " + " $scope"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateByAdmin_isOK() throws Exception {
        UserDTO userDTO = new UserDTO(1, "user1@email.com", "123", "firstname", "lastname", "avatar", false, new String[]{"ROLE_USER"});
        Gson gson = new Gson();
        String json = gson.toJson(userDTO);
        user1.setEnabled(false);

        Mockito.when(userService.updateByAdmin(1, userDTO)).thenReturn(user1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/users/1" )
                .contentType(MediaType.APPLICATION_JSON).content(json)
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
