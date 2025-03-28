package com.codeluchoro33.API_movie_reservation.service;

import com.codeluchoro33.API_movie_reservation.model.User;
import com.codeluchoro33.API_movie_reservation.repository.UserRepository;
import com.codeluchoro33.API_movie_reservation.security.user.MovieUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return MovieUserDetails.buildUserDetails(user);
    }
}