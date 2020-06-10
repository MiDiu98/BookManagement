package com.ungmydieu.bookmanagement.controllers;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.Comment;
import com.ungmydieu.bookmanagement.models.dto.CommentDTO;
import com.ungmydieu.bookmanagement.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/comments")
@PreAuthorize("isAuthenticated()")
public class CommentController {
    @Autowired
    private Converter<Comment, CommentDTO> commentCommentDTOConverter;

    @Autowired
    private CommentService commentService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<CommentDTO> getAll()
    {
        return commentCommentDTOConverter.convert(commentService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public CommentDTO getById(@PathVariable int id) {
        return commentCommentDTOConverter.convert(commentService.getById(id));
    }

    @PostMapping("/{postId}")
    @Secured("ROLE_USER")
    public CommentDTO create(Principal principal, @PathVariable int postId, @RequestBody CommentDTO commentDTO) {
        return commentCommentDTOConverter.convert(commentService.create(principal, postId, commentDTO));
    }

    @PutMapping("/{postId}/{id}")
    @Secured("ROLE_USER")
    public CommentDTO update(Principal principal, @PathVariable int postId, @PathVariable int id, @RequestBody CommentDTO commentDTO) {
        return commentCommentDTOConverter.convert(commentService.update(principal, id, commentDTO));
    }


    @DeleteMapping("/{postId}/{id}")
    @Secured("ROLE_USER")
    public void delete(Principal principal, @PathVariable String postId, @PathVariable int id) {
        commentService.delete(principal, id);
    }
}
