package com.ungmydieu.bookmanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;

    @NotNull
    @NotBlank(message = "Email is mandatory")
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String avatar;

    private boolean enable;

    @NonNull
    private String[] roles;

    public boolean getEnable() {
        return this.enable;
    }
}
