package com.ungmydieu.bookmanagement.services;

import com.ungmydieu.bookmanagement.models.dao.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<User> getAll();
    User getUserById(int id);
    User create(User user);
    User update(Principal principal, int id, User user);
    User updateByAdmin(int id, User user);
    void delete(int id);
}
