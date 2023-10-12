CREATE TABLE configs
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    created_on datetime NOT NULL,
    last_modified_on datetime NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    config_key VARCHAR(255) NOT NULL,
    config_value LONGTEXT NOT NULL,
    microservice_id BIGINT NOT NULL,
    encrypted BIT(1) NOT NULL,
    CONSTRAINT pk_configs PRIMARY KEY (id),
    CONSTRAINT uc_configs_microservice_id_config_key UNIQUE (microservice_id, config_key),
    CONSTRAINT FK_CONFIGS_ON_CLIENT FOREIGN KEY (microservice_id) REFERENCES microservices (id)
);