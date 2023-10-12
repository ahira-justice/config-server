package com.ahirajustice.configserver.common.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "config_fetch_log")
public class ConfigFetchLog extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Microservice microservice;
    @Lob
    @Column(nullable = false)
    private String retrievedConfig;

}
