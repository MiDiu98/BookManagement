package com.ungmydieu.bookmanagement.services;

import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.models.dto.UserPage;

import java.security.Principal;

public interface UserService {
    UserPage getUserByAdmin(boolean enabled, Integer pageNo, Integer pageSize, String sortBy, String order);
    User getUserById(int id);
    User update(Principal principal, int id, UserDTO userDTO);
    User updateByAdmin(int id, UserDTO userDTO);
    void delete(String role, Principal principal, int id);
}
