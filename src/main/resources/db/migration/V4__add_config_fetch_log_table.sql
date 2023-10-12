CREATE TABLE config_fetch_log
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    created_on datetime NOT NULL,
    last_modified_on datetime NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    microservice_id BIGINT NOT NULL,
    config_environment VARCHAR(255) NOT NULL,
    retrieved_config LONGTEXT NOT NULL,
    CONSTRAINT pk_config_fetch_log PRIMARY KEY (id),
    CONSTRAINT FK_CONFIG_FETCH_LOG_ON_MICROSERVICE FOREIGN KEY (microservice_id) REFERENCES microservices (id)
);