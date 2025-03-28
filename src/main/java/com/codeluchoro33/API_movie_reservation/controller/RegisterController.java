package com.codeluchoro33.API_movie_reservation.controller;

import com.codeluchoro33.API_movie_reservation.dto.UserDto;
import com.codeluchoro33.API_movie_reservation.exeptions.AlreadyExistsException;
import com.codeluchoro33.API_movie_reservation.model.User;
import com.codeluchoro33.API_movie_reservation.request.RequestCreateUser;
import com.codeluchoro33.API_movie_reservation.response.ApiResponse;
import com.codeluchoro33.API_movie_reservation.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/register")
public class RegisterController {
    private final IUserService userService;
    @PostMapping("/new-user")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RequestCreateUser request) {
        try {
            User user = userService.createUserLikeGuest(request);
            UserDto userDto = userService.convertUserToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Create User Success: ", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
