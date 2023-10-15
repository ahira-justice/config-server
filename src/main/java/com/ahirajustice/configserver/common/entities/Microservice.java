package com.ahirajustice.configserver.common.entities;

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
    private String baseUrl;
    @Column(nullable = false)
    private boolean isActive;

}
