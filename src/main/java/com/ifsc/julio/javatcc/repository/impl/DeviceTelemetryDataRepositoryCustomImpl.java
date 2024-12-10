package com.ifsc.julio.javatcc.repository.impl;

import com.ifsc.julio.javatcc.dto.GraphicValueDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueFilterDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueYearDTO;
import com.ifsc.julio.javatcc.entity.QDeviceTelemetryDayEntity;
import com.ifsc.julio.javatcc.entity.QDeviceTelemetryHourEntity;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryDataRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.ifsc.julio.javatcc.util.DateUtil.localDateToDate;
import static com.querydsl.core.types.dsl.Expressions.*;
import static java.util.Objects.*;

@Repository
public class DeviceTelemetryDataRepositoryCustomImpl implements DeviceTelemetryDataRepositoryCustom {

    private final QDeviceTelemetryHourEntity telemetry = QDeviceTelemetryHourEntity.deviceTelemetryHourEntity;

    private final QDeviceTelemetryDayEntity telemetryDay = QDeviceTelemetryDayEntity.deviceTelemetryDayEntity;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<GraphicValueDTO> getDataByDay(GraphicValueFilterDTO graphicValueFilterDTO) {
        JPQLQuery<GraphicValueDTO> query = new JPAQueryFactory(em)
                .select(
                    Projections.constructor(
                        GraphicValueDTO.class,
                        telemetry.date,
                        telemetry.value,
                        telemetry.key
                    )
                )
                .from(telemetry)
                .where(telemetry.key.in(graphicValueFilterDTO.getKeys()));

        if (nonNull(graphicValueFilterDTO.getStationIds()) && !graphicValueFilterDTO.getStationIds().isEmpty()) {
            query.where(telemetry.station.id.in(graphicValueFilterDTO.getStationIds()));
        }
        if (nonNull(graphicValueFilterDTO.getInitDate()) && nonNull(graphicValueFilterDTO.getFinalDate())) {
            query.where(telemetry.date.between(localDateToDate(graphicValueFilterDTO.getInitDate()),
                    localDateToDate(graphicValueFilterDTO.getFinalDate()) ));
        }

        return query.fetch();
    }

    @Override
    public List<GraphicValueDTO> getDataByMonth(GraphicValueFilterDTO graphicValueFilterDTO) {
        JPQLQuery<GraphicValueDTO> query = new JPAQueryFactory(em)
                .select(
                    Projections.constructor(
                        GraphicValueDTO.class,
                        telemetryDay.date,
                        telemetryDay.value,
                        telemetryDay.key
                    )
                )
                .from(telemetryDay)
                .where(telemetryDay.key.in(graphicValueFilterDTO.getKeys()));

        if (nonNull(graphicValueFilterDTO.getStationIds()) && !graphicValueFilterDTO.getStationIds().isEmpty()) {
            query.where(telemetryDay.station.id.in(graphicValueFilterDTO.getStationIds()));
        }
        if (nonNull(graphicValueFilterDTO.getInitDate()) && nonNull(graphicValueFilterDTO.getFinalDate())) {
            query.where(telemetryDay.date.between(localDateToDate(graphicValueFilterDTO.getInitDate()),
                    localDateToDate(graphicValueFilterDTO.getFinalDate()) ));
        }

        return query.fetch();
    }

    @Override
    public List<GraphicValueYearDTO> getDataByYear(GraphicValueFilterDTO graphicValueFilterDTO) {
        JPQLQuery<GraphicValueYearDTO> query = new JPAQueryFactory(em)
                .select(
                        Projections.constructor(
                                GraphicValueYearDTO.class,
                                Expressions.stringTemplate("TO_CHAR({0}, 'MM')", telemetryDay.date).as("date"),
                                telemetryDay.key,
                                telemetryDay.value.avg().as("value")
                        )
                )
                .from(telemetryDay)
                .where(telemetryDay.key.in(graphicValueFilterDTO.getKeys()))
                .groupBy(
                        Expressions.stringTemplate("TO_CHAR({0}, 'MM')", telemetryDay.date),
                        telemetryDay.key
                );

        if (nonNull(graphicValueFilterDTO.getStationIds()) && !graphicValueFilterDTO.getStationIds().isEmpty()) {
            query.where(telemetryDay.station.id.in(graphicValueFilterDTO.getStationIds()));
        }
        if (nonNull(graphicValueFilterDTO.getInitDate()) && nonNull(graphicValueFilterDTO.getFinalDate())) {
            query.where(telemetryDay.date.between(localDateToDate(graphicValueFilterDTO.getInitDate()),
                    localDateToDate(graphicValueFilterDTO.getFinalDate())));
        }

        return query.fetch();
    }
}
