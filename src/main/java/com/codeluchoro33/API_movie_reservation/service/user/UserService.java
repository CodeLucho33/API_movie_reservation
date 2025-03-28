package com.codeluchoro33.API_movie_reservation.service.user;

import com.codeluchoro33.API_movie_reservation.dto.UserDto;
import com.codeluchoro33.API_movie_reservation.enums.RoleUser;
import com.codeluchoro33.API_movie_reservation.exeptions.ResourceNotFoundException;
import com.codeluchoro33.API_movie_reservation.model.Role;
import com.codeluchoro33.API_movie_reservation.model.User;
import com.codeluchoro33.API_movie_reservation.repository.RoleRepository;
import com.codeluchoro33.API_movie_reservation.repository.UserRepository;
import com.codeluchoro33.API_movie_reservation.request.RequestCreateUser;
import com.codeluchoro33.API_movie_reservation.request.RequestCreateUserLikeAdmin;
import com.codeluchoro33.API_movie_reservation.request.RequestUpdateUserLikeAdmin;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUserLikeAdmin(RequestCreateUserLikeAdmin request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req ->{
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    // ✅ se crea un rol prexistente en la base de datos


                    Role role = roleRepository.findByRoleUser(req.getRoleUser())
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found")); // ⛔ No se crea un nuevo rol
                    user.setRoles(Collections.singleton(role)); // Agregamos el rol

                    return userRepository.save(user);

                })
                .orElseThrow(() -> new ResourceNotFoundException("User already exists!"));
    }

    @Override
    public User updateUserLikeAdmin(RequestUpdateUserLikeAdmin request, Long userId) {
       return userRepository.findById(userId).map(existingUser ->{
           existingUser.setFirstName(request.getFirstName());
           existingUser.setLastName(request.getLastName());
           existingUser.setEmail(request.getEmail());
           // ✅ Solo cambiar la contraseña si se envió una nueva
           if (request.getPassword() != null && !request.getPassword().isEmpty()) {
               existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
           }

           // ✅ Solo permitir roles que ya existen en la base de datos
           if (request.getRoleUser() != null) {
               Role role = roleRepository.findByRoleUser(request.getRoleUser())
                       .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

               existingUser.setRoles(new HashSet<>(List.of(role))); // ✅ colección mutable
           }

           return userRepository.save(existingUser);
       }).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    @Override
    public void deleteUser(Long userId) {
         userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, ()-> {
                    throw new ResourceNotFoundException("User not found");
                });
    }

    @Override
    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        // 🔹 Convertir Collection<Role> a un solo RoleUser (asumiendo que solo tiene un rol)
        if (!user.getRoles().isEmpty()) {
            userDto.setRole(user.getRoles().iterator().next().getRoleUser());
        }

        return userDto;
    }


    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUserLikeGuest(RequestCreateUser request) {
        // 1. Buscar el rol USER en la BD
        Role userRole = roleRepository.findByRoleUser(RoleUser.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role USER no encontrado"));

        // 2. Crear nuevo usuario
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 3. Asignar rol
        user.setRoles(Set.of(userRole));

        // 4. Guardar usuario
       return userRepository.save(user);

    }
}
