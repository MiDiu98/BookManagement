package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.models.dto.AuthToken;
import com.ungmydieu.bookmanagement.models.dao.Login;
import com.ungmydieu.bookmanagement.models.dto.Register;
import com.ungmydieu.bookmanagement.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        final AuthToken authToken = authenticationService.login(login);
        return ResponseEntity.ok(authToken);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid Register register) {
        authenticationService.register(register);
    }
}
