package com.ahirajustice.configserver.modules.user.services.impl;

import com.ahirajustice.configserver.common.entities.Role;
import com.ahirajustice.configserver.common.entities.User;
import com.ahirajustice.configserver.common.enums.Roles;
import com.ahirajustice.configserver.common.exceptions.BadRequestException;
import com.ahirajustice.configserver.common.exceptions.NotFoundException;
import com.ahirajustice.configserver.common.repositories.RoleRepository;
import com.ahirajustice.configserver.common.repositories.UserRepository;
import com.ahirajustice.configserver.modules.user.services.UserService;
import com.ahirajustice.configserver.modules.user.viewmodels.UserViewModel;
import com.ahirajustice.configserver.modules.user.queries.SearchUsersQuery;
import com.ahirajustice.configserver.modules.user.requests.CreateUserRequest;
import com.ahirajustice.configserver.modules.user.requests.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
     public Page<UserViewModel> searchUsers(SearchUsersQuery query) {
        return userRepository.findAll(query.getPredicate(), query.getPageable()).map(UserViewModel::from);
    }

    @Override
    public UserViewModel getUser(long id) {
        Optional<User> userExists = userRepository.findById(id);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        return UserViewModel.from(userExists.get());
    }

    @Override
    public UserViewModel createUser(CreateUserRequest request) {
        return UserViewModel.from(createUser(request, Roles.USER));
    }

    @Override
    public User createUser(CreateUserRequest request, Roles role) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException(String.format("User with username: '%s' already exists", request.getUsername()));
        }

        User user = buildUser(request);

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Role userRole = roleRepository.findByName(role.name()).orElse(null);
        user.setPassword(encryptedPassword);
        user.setRole(userRole);

        return userRepository.save(user);
    }

    @Override
    public UserViewModel updateUser(UpdateUserRequest request, long id) {
        Optional<User> userExists = userRepository.findById(id);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        User user = userExists.get();

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return UserViewModel.from(userRepository.save(user));
    }

    private User buildUser(CreateUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

}
