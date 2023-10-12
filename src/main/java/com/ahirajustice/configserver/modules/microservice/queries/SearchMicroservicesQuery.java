package com.ahirajustice.configserver.modules.microservice.queries;

import com.ahirajustice.configserver.common.entities.BaseEntity;
import com.ahirajustice.configserver.common.entities.Microservice;
import com.ahirajustice.configserver.common.entities.QMicroservice;
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
public class SearchMicroservicesQuery extends BaseQuery {

    private String identifier;
    private Boolean isActive;

    @Override
    protected Class<? extends BaseEntity> getSortEntityClass() {
        return Microservice.class;
    }

    @Override
    protected Predicate getPredicate(BooleanExpression expression) {
        if (StringUtils.isNotBlank(identifier)) {
            expression = expression.and(QMicroservice.microservice.identifier.contains(identifier));
        }

        if (isActive != null) {
            expression = expression.and(QMicroservice.microservice.isActive.eq(isActive));
        }

        return expression;
    }

}
