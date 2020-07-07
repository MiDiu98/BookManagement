package com.ungmydieu.bookmanagement.converters.comments;

import com.ungmydieu.bookmanagement.converters.bases.Converter;
import com.ungmydieu.bookmanagement.models.dao.Comment;
import com.ungmydieu.bookmanagement.models.dto.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentDaoToCommentDtoConverter extends Converter<Comment, CommentDTO> {
    @Override
    public CommentDTO convert(Comment source) {
        CommentDTO commentDTO = new CommentDTO(source.getMessage());

        commentDTO.setId(source.getId());
        commentDTO.setFullname(source.getUser().getFirstName() + " " + source.getUser().getLastName());
        commentDTO.setAvatarUser(source.getUser().getAvatar());
        commentDTO.setCreateAt(source.getCreateAt());
        commentDTO.setUpdateAt(source.getUpdateAt());
        commentDTO.setBookId(source.getBook().getId());
        commentDTO.setUserId(source.getUser().getId());
        return commentDTO;
    }
}
