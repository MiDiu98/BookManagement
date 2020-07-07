package com.ungmydieu.bookmanagement.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CommentDTO {

    private int id;

    @NonNull
    @NotBlank
    private String message;

    private String fullname;

    private String avatarUser;

    private LocalDateTime createAt;

    private  LocalDateTime updateAt;

    private int bookId;

    private int userId;
}
