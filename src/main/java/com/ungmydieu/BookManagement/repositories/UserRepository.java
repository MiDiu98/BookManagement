package com.ungmydieu.BookManagement.repositories;

import com.ungmydieu.BookManagement.models.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
