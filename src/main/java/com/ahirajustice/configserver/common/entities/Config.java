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
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "configs",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"microservice_id", "configKey"})}
)
public class Config extends BaseEntity {

    @Column(nullable = false)
    private String configKey;
    @Lob
    @Column(nullable = false)
    private String configValue;
    @Column(nullable = false)
    private Boolean encrypted;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Microservice microservice;

}
