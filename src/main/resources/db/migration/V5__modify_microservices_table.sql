ALTER TABLE microservices
    DROP COLUMN base_url;

ALTER TABLE microservices
    ADD restart_config_json text NOT NULL;