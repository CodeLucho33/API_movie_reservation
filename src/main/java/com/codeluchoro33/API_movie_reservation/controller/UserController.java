package com.codeluchoro33.API_movie_reservation.controller;


import com.codeluchoro33.API_movie_reservation.dto.UserDto;
import com.codeluchoro33.API_movie_reservation.exeptions.AlreadyExistsException;
import com.codeluchoro33.API_movie_reservation.exeptions.ResourceNotFoundException;
import com.codeluchoro33.API_movie_reservation.model.User;
import com.codeluchoro33.API_movie_reservation.request.RequestCreateUserLikeAdmin;
import com.codeluchoro33.API_movie_reservation.request.RequestUpdateUserLikeAdmin;
import com.codeluchoro33.API_movie_reservation.response.ApiResponse;
import com.codeluchoro33.API_movie_reservation.service.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add-user")
    public ResponseEntity<ApiResponse> createUser(@RequestBody RequestCreateUserLikeAdmin request){

        try {
            User user = userService.createUserLikeAdmin(request);
            UserDto userDto = userService.convertUserToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Create User Success", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-user/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody RequestUpdateUserLikeAdmin request, @PathVariable Long userId){
        try {
            User user = userService.updateUserLikeAdmin(request, userId);
            UserDto userDto = userService.convertUserToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long userId){
      try {
          userService.deleteUser(userId);
          return ResponseEntity.ok(new ApiResponse("Delete User Success", userId));
      } catch (ResourceNotFoundException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
      }
  }
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/get-user-by-id/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
      try {
          User user = userService.getUserById(userId);
          UserDto userDto = userService.convertUserToUserDto(user);
          return ResponseEntity.ok(new ApiResponse("Get User Success: ", userDto));
      } catch (ResourceNotFoundException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(new ApiResponse(e.getMessage(), null));
      }
  }


}
