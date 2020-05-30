package com.ungmydieu.bookmanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;

    @NotBlank(message = "Email is mandatory")
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String avatar;

    private boolean enable;

    private String[] roles;

    public boolean getEnable() {
        return this.enable;
    }
}
