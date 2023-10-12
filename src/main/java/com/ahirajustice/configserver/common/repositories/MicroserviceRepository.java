package com.ahirajustice.configserver.common.repositories;

import com.ahirajustice.configserver.common.entities.Microservice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MicroserviceRepository extends JpaRepository<Microservice, Long>, QuerydslPredicateExecutor<Microservice> {

    boolean existsByIdentifier(String identifier);

    Optional<Microservice> findByIdentifier(String identifier);

}
