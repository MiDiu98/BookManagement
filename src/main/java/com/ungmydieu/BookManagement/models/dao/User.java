package com.ungmydieu.bookmanagement.models.dao;

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
@Table(name="users")
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

    @JsonAlias("first_name")
    private String firstName;

    @JsonAlias("last_name")
    private String lastName;

    @Column(nullable = false)
    @NonNull
    private boolean enabled;

    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
    private Set<Role> roles;
}
