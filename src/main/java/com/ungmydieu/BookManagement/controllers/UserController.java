package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.constants.RoleConstants;
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
    @Secured("ROLE_ADMIN")
    public List<UserDTO> getAll() {
        return userUserDTOConverter.convert(userService.getAll());
    }

    @GetMapping("/enabled")
    @PreAuthorize("permitAll()")
    public List<UserDTO> getEnabledUser() {
        return userUserDTOConverter.convert(userService.getUserByAdmin(true, "firstName", "asc"));
    }

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public List<UserDTO> getUserByAdmin(
            @RequestParam boolean enabled,
            @RequestParam String sortBy,
            @RequestParam String order) {
        return userUserDTOConverter.convert(userService.getUserByAdmin(enabled, sortBy, order));
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public UserDTO getUserById(@PathVariable int id) {
        return userUserDTOConverter.convert(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public UserDTO update(Principal principal, @PathVariable int id, @RequestBody UserDTO userDTO) {
        return userUserDTOConverter.convert(userService.update(principal, id, userDTO));

    }

    @PutMapping("/admin/{id}")
    @Secured("ROLE_ADMIN")
    public UserDTO updateByAdmin(@PathVariable int id, @RequestBody UserDTO userDTO) {
        return userUserDTOConverter.convert(userService.updateByAdmin(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public void delete(Principal principal, @PathVariable int id) {
        userService.delete(RoleConstants.USER, principal, id);
    }

    @DeleteMapping("/admin/{id}")
    @Secured("ROLE_ADMIN")
    public void deleteByAdmin(Principal principal, @PathVariable int id) {
        userService.delete(RoleConstants.ADMIN, principal, id);
    }
}
