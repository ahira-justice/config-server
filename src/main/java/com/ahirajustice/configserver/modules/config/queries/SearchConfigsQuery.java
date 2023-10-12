package com.ahirajustice.configserver.modules.config.queries;

import com.ahirajustice.configserver.common.entities.BaseEntity;
import com.ahirajustice.configserver.common.entities.Config;
import com.ahirajustice.configserver.common.entities.QConfig;
import com.ahirajustice.configserver.common.queries.BaseQuery;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchConfigsQuery extends BaseQuery {

    private String key;
    private String value;
    private String microserviceIdentifier;

    @Override
    protected Class<? extends BaseEntity> getSortEntityClass() {
        return Config.class;
    }

    @Override
    protected Predicate getPredicate(BooleanExpression expression) {
        if (StringUtils.isNotBlank(key)) {
            expression = expression.and(QConfig.config.configKey.contains(key));
        }

        if (StringUtils.isNotBlank(value)) {
            expression = expression.and(QConfig.config.configValue.contains(value));
        }

        if (StringUtils.isNotBlank(microserviceIdentifier)) {
            expression = expression.and(QConfig.config.microservice.identifier.contains(microserviceIdentifier));
        }

        return expression;
    }

}
