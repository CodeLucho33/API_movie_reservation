package com.codeluchoro33.API_movie_reservation.service.user;

import com.codeluchoro33.API_movie_reservation.dto.UserDto;
import com.codeluchoro33.API_movie_reservation.model.User;
import com.codeluchoro33.API_movie_reservation.request.RequestCreateUser;
import com.codeluchoro33.API_movie_reservation.request.RequestCreateUserLikeAdmin;
import com.codeluchoro33.API_movie_reservation.request.RequestUpdateUserLikeAdmin;

public interface IUserService {
    User getUserById(Long id);
    User createUserLikeAdmin(RequestCreateUserLikeAdmin request);
    User updateUserLikeAdmin(RequestUpdateUserLikeAdmin request, Long id);
    void deleteUser(Long id);
    UserDto convertUserToUserDto(User user);
    User getAuthenticatedUser();
    User createUserLikeGuest( RequestCreateUser request);
}
