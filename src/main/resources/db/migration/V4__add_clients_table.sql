CREATE TABLE clients
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_on DATETIME NOT NULL,
    last_modified_on DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    secret VARCHAR(255) NOT NULL,
    admin_email VARCHAR(255) NOT NULL,
    refresh_callback_url VARCHAR(255) NOT NULL,
    is_active BIT(1) NOT NULL,
    CONSTRAINT pk_clients PRIMARY KEY (id),
    CONSTRAINT uc_clients_identifier UNIQUE (identifier),
    CONSTRAINT uc_clients_name UNIQUE (name)
);