CREATE TABLE configs
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_on         datetime              NOT NULL,
    last_modified_on   datetime              NOT NULL,
    created_by         VARCHAR(255)          NOT NULL,
    last_modified_by   VARCHAR(255)          NOT NULL,
    is_deleted         BIT(1)                NOT NULL,
    version            INT                   NOT NULL,
    `key`              VARCHAR(255)          NOT NULL,
    `value`              VARCHAR(255)          NOT NULL,
    config_environment VARCHAR(255)          NOT NULL,
    client_id          BIGINT                NOT NULL,
    CONSTRAINT pk_configs PRIMARY KEY (id),
    CONSTRAINT uc_configs_client_id_key_config_environment UNIQUE (client_id, `key`, config_environment),
    CONSTRAINT FK_CONFIGS_ON_CLIENT FOREIGN KEY (client_id) REFERENCES clients (id)
);