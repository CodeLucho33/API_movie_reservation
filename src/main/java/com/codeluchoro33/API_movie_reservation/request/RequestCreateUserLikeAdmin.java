package com.codeluchoro33.API_movie_reservation.request;

import com.codeluchoro33.API_movie_reservation.enums.RoleUser;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@NotBlank
@Data
public class RequestCreateUserLikeAdmin {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private RoleUser roleUser;

}
