package com.ungmydieu.bookmanagement.repositories;

import com.ungmydieu.bookmanagement.models.dao.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    List<User> findAllByEnabled(boolean enabled, Sort sort);
}
