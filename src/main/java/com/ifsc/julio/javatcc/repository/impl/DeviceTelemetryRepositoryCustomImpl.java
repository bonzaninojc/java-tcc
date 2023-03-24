package com.ifsc.julio.javatcc.repository.impl;

import com.ifsc.julio.javatcc.dto.query.*;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryRepositoryCustom;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.util.*;

public class DeviceTelemetryRepositoryCustomImpl implements DeviceTelemetryRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    // TODO - Deixar empressoes genericas

    @Override
    public List<DailyAverageDTO> getDailyAverages(AverageSearchDTO averageSearch) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<DailyAverageDTO> query = builder.createQuery(DailyAverageDTO.class);
        Root<DeviceTelemetryEntity> root = query.from(DeviceTelemetryEntity.class);

        Expression<Integer> dayExpression = builder.function("day", Integer.class, root.get("date"));

        query
            .select(builder.construct(DailyAverageDTO.class, dayExpression, builder.avg(root.get("value"))))
            .where(builder.between(root.get("date"), averageSearch.getStart(), averageSearch.getEnd()))
            .where(builder.equal(root.get("key"), averageSearch.getKey()))
            .groupBy(dayExpression);

        TypedQuery<DailyAverageDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public List<WeeklyAverageDTO> getWeeklyAverages(AverageSearchDTO averageSearch) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<WeeklyAverageDTO> query = builder.createQuery(WeeklyAverageDTO.class);
        Root<DeviceTelemetryEntity> root = query.from(DeviceTelemetryEntity.class);

        Expression<Integer> monthExpression = builder.function("month", Integer.class, root.get("date"));

        query
            .select(builder.construct(WeeklyAverageDTO.class, monthExpression, builder.avg(root.get("value"))))
            .where(builder.between(root.get("date"), averageSearch.getStart(), averageSearch.getEnd()))
            .where(builder.equal(root.get("key"), averageSearch.getKey()))
            .groupBy(monthExpression);

        TypedQuery<WeeklyAverageDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public List<MonthlyAverageDTO> getMonthlyAverages(AverageSearchDTO averageSearch) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<MonthlyAverageDTO> query = builder.createQuery(MonthlyAverageDTO.class);
        Root<DeviceTelemetryEntity> root = query.from(DeviceTelemetryEntity.class);

        Expression<Integer> yearExpression = builder.function("year", Integer.class, root.get("date"));
        Expression<Integer> monthExpression = builder.function("month", Integer.class, root.get("date"));

        query
            .select(builder.construct(MonthlyAverageDTO.class, yearExpression, monthExpression, builder.avg(root.get("value"))))
            .where(builder.between(root.get("date"), averageSearch.getStart(), averageSearch.getEnd()))
            .where(builder.equal(root.get("key"), averageSearch.getKey()))
            .groupBy(yearExpression, monthExpression);

        TypedQuery<MonthlyAverageDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
