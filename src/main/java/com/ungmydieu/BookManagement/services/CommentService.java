package com.ungmydieu.bookmanagement.services;

import com.ungmydieu.bookmanagement.models.dao.Comment;
import com.ungmydieu.bookmanagement.models.dto.CommentDTO;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    List<Comment> getAll();
    Comment getById(int id);
    Comment create(Principal principal, int postId, CommentDTO commentDTO);
    Comment update(Principal principal, int id, CommentDTO commentDTO);
    void delete(Principal principal, int id);
}
