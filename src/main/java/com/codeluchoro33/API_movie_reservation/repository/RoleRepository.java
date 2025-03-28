package com.codeluchoro33.API_movie_reservation.repository;

import com.codeluchoro33.API_movie_reservation.enums.RoleUser;
import com.codeluchoro33.API_movie_reservation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleUser(RoleUser roleUser);


}
