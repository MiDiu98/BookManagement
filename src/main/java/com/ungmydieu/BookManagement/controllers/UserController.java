package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/users")
@PreAuthorize("isAuthenticated()")
public class UserController {
    @Autowired
    private Converter<User, UserDTO> userUserDTOConverter;

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<UserDTO> getAll() {
        return userUserDTOConverter.convert(userService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public UserDTO getUserById(@PathVariable int id) {
        return userUserDTOConverter.convert(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public User update(Principal principal, @PathVariable int id, @RequestBody UserDTO userDTO) {
        return userService.update(principal, id, userDTO);

    }

    @PutMapping("/admin/{id}")
    @Secured("ROLE_ADMIN")
    public User updateByAdmin(@PathVariable int id, @RequestBody UserDTO userDTO) {
        return userService.updateByAdmin(id, userDTO);

    }
}
