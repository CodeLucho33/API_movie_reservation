package com.codeluchoro33.API_movie_reservation.request;

import com.codeluchoro33.API_movie_reservation.enums.RoleUser;
import lombok.Data;

@Data
public class RequestUpdateUserLikeAdmin {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private RoleUser roleUser;
}
