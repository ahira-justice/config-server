package com.ahirajustice.configserver.modules.configfetchlog.queries;

import com.ahirajustice.configserver.common.entities.BaseEntity;
import com.ahirajustice.configserver.common.entities.ConfigFetchLog;
import com.ahirajustice.configserver.common.entities.QConfigFetchLog;
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
public class SearchConfigFetchLogQuery extends BaseQuery {

    private String microserviceIdentifier;

    @Override
    protected Class<? extends BaseEntity> getSortEntityClass() {
        return ConfigFetchLog.class;
    }

    @Override
    protected Predicate getPredicate(BooleanExpression expression) {
        if (StringUtils.isNotBlank(microserviceIdentifier)) {
            expression = expression.and(QConfigFetchLog.configFetchLog.microservice.identifier.contains(microserviceIdentifier));
        }

        return expression;
    }

}
