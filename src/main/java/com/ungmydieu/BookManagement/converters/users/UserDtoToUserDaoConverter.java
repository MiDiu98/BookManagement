package com.ungmydieu.bookmanagement.converters.users;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.exceptions.BadRequestException;
import com.ungmydieu.bookmanagement.models.dao.Role;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserDtoToUserDaoConverter extends Converter<UserDTO, User> {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User convert(UserDTO source) {
        User user = new User();

        user.setId(source.getId());
        user.setEmail(source.getEmail());
        user.setPassword(source.getPassword());
        user.setLastName(source.getLastName());
        user.setAvatar(source.getAvatar());
        user.setEnabled(source.getEnable());

        if (source.getRoles().length > 0) {
            user.setRoles(new HashSet<>());
            for (String i : source.getRoles()){
                Role role = roleRepository.findByName(i);
                if (role != null) user.getRoles().add(role);
                else throw new BadRequestException("Invalid role " + i);
            }
        }

        return user;
    }
}
