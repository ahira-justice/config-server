package com.ahirajustice.configserver.common.repositories;

import com.ahirajustice.configserver.common.entities.ConfigFetchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigFetchLogRepository extends JpaRepository<ConfigFetchLog, Long>, QuerydslPredicateExecutor<ConfigFetchLog> {

}
