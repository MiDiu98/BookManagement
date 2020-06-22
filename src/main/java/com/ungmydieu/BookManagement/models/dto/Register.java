package com.ungmydieu.bookmanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Register {
    @NonNull
    @NotNull
    @Email
    private String email;

    @NonNull
    @NotNull
    @NotBlank(message = "Password is mandatory")
    private String password;

    private String firstName;

    private String lastName;
}
