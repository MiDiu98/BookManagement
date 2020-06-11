package com.ungmydieu.bookmanagement.converters.users;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.repositories.RoleRepository;
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

import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {GsonAutoConfiguration.class})
public class UserDaoToUserDtoConverterTest {
    @Autowired
    private Converter<User, UserDTO> userUserDTOConverter;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void test_Converter() {
        User user = new User("userTest","123",true);
        user.setRoles(new HashSet<>());
        user.getRoles().add(roleRepository.findByName("ROLE_ADMIN"));

        UserDTO userDTO = userUserDTOConverter.convert(user);
        Assert.assertEquals(userDTO.getEmail(), "userTest");
        Assert.assertEquals(userDTO.getPassword(), null);
        Assert.assertEquals(userDTO.isEnable(), true);
    }
}
