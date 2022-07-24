package com.ahirajustice.configserver.common.entities;

import com.ahirajustice.configserver.common.enums.ConfigEnvironment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "config_fetch_log")
public class ConfigFetchLog extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConfigEnvironment configEnvironment;

    @Lob
    @Column(nullable = false)
    private String retrievedConfig;

}
