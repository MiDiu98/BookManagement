package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.constants.RoleConstants;
import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.Register;
import com.ungmydieu.bookmanagement.models.dto.UserDTO;
import com.ungmydieu.bookmanagement.models.dto.UserPage;
import com.ungmydieu.bookmanagement.services.AuthenticationService;
import com.ungmydieu.bookmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/admin/users")
public class UserAdminController {
    @Autowired
    private Converter<User, UserDTO> userUserDTOConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public UserPage getUserByAdmin(
            @RequestParam boolean enabled,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        return userService.getUserByAdmin(enabled, pageNo, pageSize, sortBy, order);
    }

    @PostMapping
    public void register(@RequestBody @Valid Register register) {
        authenticationService.register(register);
    }

    @PutMapping("/{id}")
    public UserDTO updateByAdmin(@PathVariable int id, @RequestBody UserDTO userDTO) {
        return userUserDTOConverter.convert(userService.updateByAdmin(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteByAdmin(Principal principal, @PathVariable int id) {
        userService.delete(RoleConstants.ADMIN, principal, id);
    }
}
