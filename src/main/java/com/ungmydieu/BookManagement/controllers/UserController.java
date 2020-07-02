package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.constants.RoleConstants;
import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private Converter<User, UserDTO> userUserDTOConverter;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable int id) {
        return userUserDTOConverter.convert(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public UserDTO update(Principal principal, @PathVariable int id, @RequestBody UserDTO userDTO) {
        return userUserDTOConverter.convert(userService.update(principal, id, userDTO));

    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public void delete(Principal principal, @PathVariable int id) {
        userService.delete(RoleConstants.USER, principal, id);
    }

}
