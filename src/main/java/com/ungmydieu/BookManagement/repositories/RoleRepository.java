package com.ungmydieu.BookManagement.repositories;

import com.ungmydieu.BookManagement.models.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
