package com.ungmydieu.BookManagement.services.impl;

import com.ungmydieu.BookManagement.exceptions.BadRequestException;
import com.ungmydieu.BookManagement.exceptions.NotFoundException;
import com.ungmydieu.BookManagement.models.dao.User;
import com.ungmydieu.BookManagement.repositories.UserRepository;
import com.ungmydieu.BookManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Principal principal, int id, User user) {
        verifyUserIdExits(id);
        verifyAuthor(principal, id);
        return userRepository.save(user);
    }

    @Override
    public User updateByAdmin(int id, User user) {
        verifyUserIdExits(id);
        return userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    private void verifyUserIdExits(int id) {
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
