package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.models.dto.AuthToken;
import com.ungmydieu.bookmanagement.models.dao.Login;
import com.ungmydieu.bookmanagement.models.dto.Register;
import com.ungmydieu.bookmanagement.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        final String token = authenticationService.login(login);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid Register register) {
        authenticationService.register(register);
    }
}
