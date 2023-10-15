package com.ahirajustice.configserver.common.data;

import com.ahirajustice.configserver.common.constants.SuperuserConstants;
import com.ahirajustice.configserver.common.entities.User;
import com.ahirajustice.configserver.common.properties.AppProperties;
import com.ahirajustice.configserver.common.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    private void seedSuperuserUser() {
        try {
            Optional<User> superuserExists = userRepository.findByUsername(SuperuserConstants.SUPERUSER_USERNAME);

            if (superuserExists.isPresent()) {
                return;
            }

            User superuser = new User();
            superuser.setEmail(SuperuserConstants.SUPERUSER_EMAIL);
            superuser.setUsername(SuperuserConstants.SUPERUSER_USERNAME);
            superuser.setPassword(passwordEncoder.encode(appProperties.getSuperuserPassword()));
            superuser.setFirstName(SuperuserConstants.SUPERUSER_FIRST_NAME);
            superuser.setLastName(SuperuserConstants.SUPERUSER_LAST_NAME);

            userRepository.save(superuser);
        } catch (Exception ignored) {

        }
    }

    @Override
    public void run(ApplicationArguments args) {
        seedSuperuserUser();
    }

}
