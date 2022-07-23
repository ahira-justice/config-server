package com.ahirajustice.configserver.common.repositories;

import com.ahirajustice.configserver.common.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
