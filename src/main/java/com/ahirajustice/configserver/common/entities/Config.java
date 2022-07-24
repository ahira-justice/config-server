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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "configs")
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id", "configKey", "configEnvironment"})}
)
public class Config extends BaseEntity {

    @Column(nullable = false)
    private String configKey;

    @Lob
    @Column(nullable = false)
    private String configValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConfigEnvironment configEnvironment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;

}
