package com.ahirajustice.configserver.modules.configfetchlog.queries;

import com.ahirajustice.configserver.common.entities.BaseEntity;
import com.ahirajustice.configserver.common.entities.ConfigFetchLog;
import com.ahirajustice.configserver.common.entities.QConfigFetchLog;
import com.ahirajustice.configserver.common.enums.ConfigEnvironment;
import com.ahirajustice.configserver.common.queries.BaseQuery;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchConfigFetchLogQuery extends BaseQuery {

    private ConfigEnvironment configEnvironment;
    private long clientId;

    @Override
    protected Class<? extends BaseEntity> getSortEntityClass() {
        return ConfigFetchLog.class;
    }

    @Override
    protected Predicate getPredicate(BooleanExpression expression) {
        if (configEnvironment != null) {
            expression = expression.and(QConfigFetchLog.configFetchLog.configEnvironment.eq(configEnvironment));
        }

        if (clientId > 0) {
            expression = expression.and(QConfigFetchLog.configFetchLog.client.id.eq(clientId));
        }

        return expression;
    }

}
