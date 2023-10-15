CREATE TABLE microservices
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_on DATETIME NOT NULL,
    last_modified_on DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    encrypted_secret_key MEDIUMTEXT NOT NULL,
    hashed_secret_key MEDIUMTEXT NOT NULL,
    encrypting_key MEDIUMTEXT NOT NULL,
    base_url VARCHAR(255) NOT NULL,
    is_active BIT(1) NOT NULL,
    CONSTRAINT pk_microservices PRIMARY KEY (id),
    CONSTRAINT uc_microservices_identifier UNIQUE (identifier)
);