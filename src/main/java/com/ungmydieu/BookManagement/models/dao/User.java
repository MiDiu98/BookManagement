package com.ungmydieu.BookManagement.models.dao;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NonNull
    @NotBlank
    private String email;

    @Column(nullable = false)
    @NonNull
    @NotBlank
    private String password;

    @JsonAlias("last_name")
    private String first_name;

    @JsonAlias("first_name")
    private String last_name;

    @Column(nullable = false)
    @NonNull
    private boolean enabled;

    private String avatar;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
    private Set<Role> roles;

}
