package com.ahirajustice.configserver.modules.client.queries;

import com.ahirajustice.configserver.common.entities.BaseEntity;
import com.ahirajustice.configserver.common.entities.Client;
import com.ahirajustice.configserver.common.entities.QClient;
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
public class SearchClientsQuery extends BaseQuery {

    private String identifier;
    private String name;
    private Boolean isActive;

    @Override
    protected Class<? extends BaseEntity> getSortEntityClass() {
        return Client.class;
    }

    @Override
    protected Predicate getPredicate(BooleanExpression expression) {
        if (StringUtils.isNotBlank(identifier)) {
            expression = expression.and(QClient.client.identifier.contains(identifier));
        }

        if (StringUtils.isNotBlank(name)) {
            expression = expression.and(QClient.client.name.contains(name));
        }
        if (isActive != null) {
            expression = expression.and(QClient.client.isActive.eq(isActive));
        }

        return expression;
    }

}
