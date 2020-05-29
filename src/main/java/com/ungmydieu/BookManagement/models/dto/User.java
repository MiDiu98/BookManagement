package com.ungmydieu.bookmanagement.models.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class User {
    @NotNull
    @NotBlank(message = "Email is mandatory")
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String avatar;

    @NonNull
    private boolean enable;

    @NonNull
    private String[] roles;
}
