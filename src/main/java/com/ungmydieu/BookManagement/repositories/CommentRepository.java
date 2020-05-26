package com.ungmydieu.BookManagement.repositories;

import com.ungmydieu.BookManagement.models.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
