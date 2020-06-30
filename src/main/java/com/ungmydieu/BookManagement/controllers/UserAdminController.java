package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.constants.RoleConstants;
import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/admin/users")
public class UserAdminController {
    @Autowired
    private Converter<User, UserDTO> userUserDTOConverter;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getUserByAdmin(
            @RequestParam boolean enabled,
            @RequestParam String sortBy,
            @RequestParam String order) {
        return userUserDTOConverter.convert(userService.getUserByAdmin(enabled, sortBy, order));
    }

    @PutMapping("/{id}")
    public UserDTO updateByAdmin(@PathVariable int id, @RequestBody UserDTO userDTO) {
        return userUserDTOConverter.convert(userService.updateByAdmin(id, userDTO));
    }

    @DeleteMapping("/admin/{id}")
    public void deleteByAdmin(Principal principal, @PathVariable int id) {
        userService.delete(RoleConstants.ADMIN, principal, id);
    }
}
