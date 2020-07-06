package com.ungmydieu.bookmanagement.converters.users;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {GsonAutoConfiguration.class})
public class UserDtoToUserDaoConverterTest {
    @Autowired
    private Converter<UserDTO, User> userDTOUserConverter;

    @Test
    public void test_converter() {
        UserDTO userDTO = new UserDTO(1, "admin@email.com", "123", "firstname", "lastname", "avatar_path", true, new String[]{"ROLE_USER", "ROLE_ADMIN"});

        User user = userDTOUserConverter.convert(userDTO);
        Assert.assertEquals(user.getFirstName(), "firstname");
    }
}
