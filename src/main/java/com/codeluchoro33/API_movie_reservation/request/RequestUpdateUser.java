package com.codeluchoro33.API_movie_reservation.request;

import lombok.Data;

@Data
public class RequestUpdateUser {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
