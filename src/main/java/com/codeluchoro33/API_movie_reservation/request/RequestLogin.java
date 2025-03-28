package com.codeluchoro33.API_movie_reservation.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RequestLogin {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
