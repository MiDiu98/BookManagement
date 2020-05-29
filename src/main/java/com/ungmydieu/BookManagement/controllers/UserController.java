package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User update(Principal principal, @PathVariable int id, @RequestBody UserDTO userDTO) {
        return userService.update(principal, id, userDTO);

    }

    @PutMapping("/admin/{id}")
    @Secured("ROLE_ADMIN")
    public User updateByAdmin(@PathVariable int id, @RequestBody UserDTO userDTO) {
        return userService.updateByAdmin(id, userDTO);

    }
}
