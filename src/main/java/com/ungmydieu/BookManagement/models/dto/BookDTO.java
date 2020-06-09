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

    public boolean getEnable() {
        return this.enabled;
    }
}
