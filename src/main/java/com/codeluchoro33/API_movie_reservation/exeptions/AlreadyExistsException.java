package com.codeluchoro33.API_movie_reservation.exeptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
