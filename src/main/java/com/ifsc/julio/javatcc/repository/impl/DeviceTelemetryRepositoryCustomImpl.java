package com.ifsc.julio.javatcc.repository.impl;

import com.ifsc.julio.javatcc.dto.query.*;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import com.ifsc.julio.javatcc.enumeration.TimePeriod;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryRepositoryCustom;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.util.*;

public class DeviceTelemetryRepositoryCustomImpl implements DeviceTelemetryRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<AverageDTO> getAverage(AverageSearchDTO averageSearch) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<AverageDTO> query = builder.createQuery(AverageDTO.class);
        Root<DeviceTelemetryEntity> root = query.from(DeviceTelemetryEntity.class);
        Expression<?> expressions = getExpressions(averageSearch.getTimePeriod(), builder, root.get("date"));

        query.select(builder.construct(AverageDTO.class, expressions, builder.avg(root.get("value"))))
             .where(builder.between(root.get("date"), averageSearch.getStart(), averageSearch.getEnd()))
             .where(builder.equal(root.get("key"), averageSearch.getKey()))
             .groupBy(expressions);

        TypedQuery<AverageDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    private Expression<?> getExpressions(TimePeriod timePeriod, CriteriaBuilder builder, Path<DeviceTelemetryEntity> path) {
        switch(timePeriod) {
            default:
            case DAILY:
                return builder.function("day", Integer.class, path);
            case WEEKLY:
                return builder.function("week", Integer.class, path);
            case MONTHLY:
                Expression<Integer> yearExpression = builder.function("year", Integer.class, path);
                Expression<Integer> monthExpression = builder.function("month", Integer.class, path);
                return (Expression<?>) builder.array(yearExpression, monthExpression);
        }
    }
}
