package com.ungmydieu.bookmanagement.services.impl;

import com.ungmydieu.bookmanagement.constants.RoleConstants;
import com.ungmydieu.bookmanagement.exceptions.BadRequestException;
import com.ungmydieu.bookmanagement.exceptions.NotFoundException;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.repositories.RoleRepository;
import com.ungmydieu.bookmanagement.repositories.UserRepository;
import com.ungmydieu.bookmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUserByEnabled(boolean enabled) {
        return userRepository.findAllByEnabled(enabled);
    }

    @Override
    public User getUserById(int id) {
        verifyUserIdExist(id);
        return userRepository.findById(id).get();
    }

    @Override
    public User update(Principal principal, int id, UserDTO userDTO) {
        verifyUserIdExist(id);
        verifyAuthor(principal, id);

        User user = userRepository.getOne(id);
        if (userDTO.getPassword() != null) user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAvatar(userDTO.getAvatar());

        return userRepository.save(user);
    }

    @Override
    public User updateByAdmin(int id, UserDTO userDTO) {
        verifyUserIdExist(id);

        User user = userRepository.getOne(id);
        user.setEnabled(userDTO.isEnable());
        user.setRoles(new HashSet<>());
        for (String role : userDTO.getRoles()){
            user.getRoles().add(roleRepository.findByName(role));
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(String role, Principal principal, int id) {
        verifyUserIdExist(id);
        if (role.equals(RoleConstants.USER)) verifyAuthor(principal, id);

        userRepository.deleteById(id);
    }

    private void verifyUserIdExist(int id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(String.format("User id %d is not found", id));
        }
    }

    private void verifyAuthor(Principal principal, int id) {
        User currentUser = userRepository.findByEmail(principal.getName());
        if (!currentUser.equals(userRepository.findById(id).get())) {
            throw new BadRequestException(String.format("User id %d can not update user info", id));
        }
    }
}
