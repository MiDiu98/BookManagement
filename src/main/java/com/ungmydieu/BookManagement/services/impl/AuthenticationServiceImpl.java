package com.ungmydieu.bookmanagement.services.impl;

import com.ungmydieu.bookmanagement.exceptions.BadRequestException;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.Register;
import com.ungmydieu.bookmanagement.repositories.RoleRepository;
import com.ungmydieu.bookmanagement.repositories.UserRepository;
import com.ungmydieu.bookmanagement.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void register(Register register) {
        isEmailExisted(register.getEmail());
        User user = new User(register.getEmail(), new BCryptPasswordEncoder().encode(register.getPassword()), true);
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());

        user.setRoles(new HashSet<>());
        user.getRoles().add(roleRepository.findByName("ROLE_USER"));

        userRepository.save(user);
    }

    private void isEmailExisted(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new BadRequestException("Email is used to be register");
        }
    }
}
