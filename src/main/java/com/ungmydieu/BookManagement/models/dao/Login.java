package com.ungmydieu.bookmanagement.models.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {
    private String email;
    private String password;
}
