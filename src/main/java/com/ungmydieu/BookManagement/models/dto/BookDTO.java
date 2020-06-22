package com.ungmydieu.bookmanagement.models.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private int id;

    @NonNull
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NonNull
    @NotBlank(message = "Author is mandatory")
    private String author;

    private String description;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private String image;

    private boolean enabled;

    private int userId;
}
