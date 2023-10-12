package com.ahirajustice.configserver.modules.user.services.impl;

import com.ahirajustice.configserver.common.entities.User;
import com.ahirajustice.configserver.common.exceptions.BadRequestException;
import com.ahirajustice.configserver.common.exceptions.NotFoundException;
import com.ahirajustice.configserver.common.repositories.UserRepository;
import com.ahirajustice.configserver.modules.user.queries.SearchUsersQuery;
import com.ahirajustice.configserver.modules.user.requests.CreateUserRequest;
import com.ahirajustice.configserver.modules.user.requests.UpdateUserRequest;
import com.ahirajustice.configserver.modules.user.services.UserService;
import com.ahirajustice.configserver.modules.user.viewmodels.UserViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
     public Page<UserViewModel> searchUsers(SearchUsersQuery query) {
        return userRepository.findAll(query.getPredicate(), query.getPageable()).map(UserViewModel::from);
    }

    @Override
    public UserViewModel getUser(long id) {
        Optional<User> userExists = userRepository.findById(id);

        if (userExists.isEmpty()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        return UserViewModel.from(userExists.get());
    }

    @Override
    public UserViewModel createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new BadRequestException(String.format("User with username: '%s' already exists", request.getUsername()));

        User user = buildUser(request);
        return UserViewModel.from(userRepository.save(user));
    }

    @Override
    public UserViewModel updateUser(UpdateUserRequest request, long id) {
        Optional<User> userExists = userRepository.findById(id);

        if (userExists.isEmpty()) {
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
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

}
