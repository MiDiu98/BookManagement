package com.ungmydieu.bookmanagement.services.impl;

import com.ungmydieu.bookmanagement.exceptions.BadRequestException;
import com.ungmydieu.bookmanagement.exceptions.NotFoundException;
import com.ungmydieu.bookmanagement.models.dao.Comment;
import com.ungmydieu.bookmanagement.models.dao.User;
import com.ungmydieu.bookmanagement.models.dto.CommentDTO;
import com.ungmydieu.bookmanagement.repositories.BookRepository;
import com.ungmydieu.bookmanagement.repositories.CommentRepository;
import com.ungmydieu.bookmanagement.repositories.UserRepository;
import com.ungmydieu.bookmanagement.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;


    @Override
    public List<Comment> getAllByBook(int bookId) {
        verifyBookIdExist(bookId);
        return commentRepository.findAllByBook(bookRepository.getOne(bookId));
    }

    @Override
    public Comment getById(int id) {
        verifyCommentIdExist(id);
        return commentRepository.findById(id).get();
    }

    @Override
    public Comment create(Principal principal, int bookId, CommentDTO commentDTO) {
        verifyBookIdExist(bookId);

        Comment comment = new Comment(commentDTO.getMessage(), LocalDateTime.now(), LocalDateTime.now());
        comment.setBook(bookRepository.getOne(bookId));
        comment.setUser(userRepository.findByEmail(principal.getName()));
        commentRepository.save(comment);
        return commentRepository.getOne(comment.getId());
    }

    @Override
    public Comment update(Principal principal, int id, CommentDTO commentDTO) {
        verifyCommentIdExist(id);
        verifyAuthor(principal, id);

        Comment comment = commentRepository.getOne(id);
        comment.setMessage(commentDTO.getMessage());
        comment.setUpdateAt(LocalDateTime.now());
        commentRepository.save(comment);
        return commentRepository.getOne(id);
    }

    @Override
    public void delete(Principal principal, int id) {
        verifyCommentIdExist(id);
        verifyAuthor(principal, id);

        commentRepository.delete(commentRepository.getOne(id));
    }

    private void verifyCommentIdExist(int id) {
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException(String.format("Comment id %d is not found", id));
        }
    }

    private void verifyAuthor(Principal principal, int id) {
        User currentUser = userRepository.findByEmail(principal.getName());
        if (!currentUser.equals(commentRepository.findById(id).get().getUser())) {
            throw new BadRequestException(String.format("The only author can modify"));
        }
    }

    private void verifyBookIdExist(int id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException(String.format("Book id %d is not found", id));
        }
    }
}
