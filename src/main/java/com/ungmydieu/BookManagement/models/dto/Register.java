package com.ungmydieu.bookmanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Register {
    @NotNull
    @Email
    private String email;

    @NotNull
    @NotBlank(message = "Password is mandatory")
    private String password;

    private String firstName;

    private String lastName;
}
