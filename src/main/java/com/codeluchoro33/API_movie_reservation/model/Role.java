package com.codeluchoro33.API_movie_reservation.model;

import com.codeluchoro33.API_movie_reservation.enums.RoleUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Enumerated(EnumType.STRING)
    private  RoleUser roleUser;


    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public Role(RoleUser roleUser) {
        this.roleUser = roleUser;
    }
}
