package com.ungmydieu.bookmanagement.models.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class Register {
    @NotNull
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotNull
    @NotBlank(message = "Password is mandatory")
    private String password;

    private String firstName;

    private String lastName;
}