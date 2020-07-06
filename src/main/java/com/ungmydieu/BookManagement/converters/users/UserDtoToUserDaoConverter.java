package com.ungmydieu.bookmanagement.converters.users;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserDaoConverter extends Converter<UserDTO, User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User convert(UserDTO source) {
        User user = userRepository.getOne(source.getId());

        if (source.getPassword() != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(source.getPassword()));
        }

        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setAvatar(source.getAvatar());

        return user;
    }
}
