package com.ungmydieu.bookmanagement.repositories;

import com.ungmydieu.bookmanagement.models.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
