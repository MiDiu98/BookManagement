package com.ungmydieu.bookmanagement.services;

import com.ungmydieu.bookmanagement.models.dao.Login;
import com.ungmydieu.bookmanagement.models.dto.Register;

public interface AuthenticationService {
    String login(Login login);
    void register(Register register);
}
