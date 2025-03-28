package com.codeluchoro33.API_movie_reservation.repository;

import com.codeluchoro33.API_movie_reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
