package com.ahirajustice.configserver.common.repositories;

import com.ahirajustice.configserver.common.entities.Config;
import com.ahirajustice.configserver.common.entities.Microservice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long>, QuerydslPredicateExecutor<Config> {

    boolean existsByConfigKeyAndMicroservice(String key, Microservice microservice);

    List<Config> findByMicroservice(Microservice microservice);

}
