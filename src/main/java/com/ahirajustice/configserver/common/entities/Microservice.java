package com.ahirajustice.configserver.common.entities;

import com.ahirajustice.configserver.common.utils.ObjectMapperUtils;
import com.ahirajustice.configserver.modules.microservice.models.RestartConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "microservices")
public class Microservice extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String identifier;
    @Lob
    @Column(nullable = false)
    private String encryptedSecretKey;
    @Lob
    @Column(nullable = false)
    private String hashedSecretKey;
    @Lob
    @Column(nullable = false)
    private String encryptingKey;
    @Column(nullable = false)
    private boolean isActive;
    @Lob
    @Column(nullable = false)
    private String restartConfigJson;

    public void setRestartConfig(RestartConfig restartConfig) {
        this.restartConfigJson = ObjectMapperUtils.serialize(new ObjectMapper(), restartConfig);
    }

    public RestartConfig getRestartConfig() {
        return ObjectMapperUtils.deserialize(new ObjectMapper(), this.restartConfigJson, RestartConfig.class);
    }

}
