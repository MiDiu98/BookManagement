package com.ungmydieu.bookmanagement.services;

import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<User> getAll();
    List<User> getUserByAdmin(boolean enabled, String sortBy, String order);
    User getUserById(int id);
    User update(Principal principal, int id, UserDTO userDTO);
    User updateByAdmin(int id, UserDTO userDTO);
    void delete(String role, Principal principal, int id);
}
