package com.ahirajustice.configserver.common.repositories;

import com.ahirajustice.configserver.common.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, QuerydslPredicateExecutor<Client> {

    boolean existsByIdentifier(String identifier);

    Optional<Client> findByIdentifier(String identifier);

}
