package com.ungmydieu.bookmanagement.converters.users;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.exceptions.BadRequestException;
import com.ungmydieu.bookmanagement.models.dao.Role;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDaoToUserDtoConverter extends Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User source) throws BadRequestException {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(source.getId());
        userDTO.setEmail(source.getEmail());
        userDTO.setPassword(null);
        userDTO.setLastName(source.getLastName());
        userDTO.setFirstName(source.getFirstName());
        userDTO.setAvatar(source.getAvatar());
        userDTO.setEnable(source.isEnabled());

        String[] roles = new String[source.getRoles().size()];
        int i = 0;
        for (Role role : source.getRoles())
            roles[i++] = role.getName();
        userDTO.setRoles(roles);

        return userDTO;
    }
}
