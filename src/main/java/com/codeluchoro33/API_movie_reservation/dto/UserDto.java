package com.codeluchoro33.API_movie_reservation.dto;

import com.codeluchoro33.API_movie_reservation.enums.RoleUser;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private RoleUser role;
}
