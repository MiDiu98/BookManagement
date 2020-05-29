package com.ungmydieu.bookmanagement.models.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @NotBlank
    private String title;

    @NonNull
    @NotBlank
    private String author;

    private String description;

    @NonNull
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @NonNull
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    private String image;

    @NonNull
    @NotBlank
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
