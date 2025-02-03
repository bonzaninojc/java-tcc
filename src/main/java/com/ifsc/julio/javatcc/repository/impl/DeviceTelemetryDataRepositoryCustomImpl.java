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

import static com.ifsc.julio.javatcc.util.DateUtil.*;
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
                        Expressions.stringTemplate("to_char({0}, 'HH24')", telemetry.date),
                        telemetry.value,
                        telemetry.key,
                        telemetry.station.id
                    )
                )
                .from(telemetry)
                .where(telemetry.key.in(graphicValueFilterDTO.getKeys()));

        if (nonNull(graphicValueFilterDTO.getStationIds()) && !graphicValueFilterDTO.getStationIds().isEmpty()) {
            query.where(telemetry.station.id.in(graphicValueFilterDTO.getStationIds()));
        }

        if (nonNull(graphicValueFilterDTO.getInitDate())) {
            Date initDate = localDateToDate(graphicValueFilterDTO.getInitDate());
            Date startOfDay = getStartOfDay(initDate);
            Date endOfDay = getEndOfDay(initDate);
            query.where(telemetry.date.between(startOfDay, endOfDay));
        }

        return query.fetch();
    }

    @Override
    public List<GraphicValueDTO> getDataByMonth(GraphicValueFilterDTO graphicValueFilterDTO) {
        JPQLQuery<GraphicValueDTO> query = new JPAQueryFactory(em)
                .select(
                    Projections.constructor(
                        GraphicValueDTO.class,
                         Expressions.stringTemplate("to_char({0}, 'DD')", telemetryDay.date),
                        telemetryDay.value,
                        telemetryDay.key,
                        telemetryDay.station.id
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
                                telemetryDay.value.avg().as("value"),
                                telemetryDay.key,
                                telemetryDay.station.id

                        )
                )
                .from(telemetryDay)
                .where(telemetryDay.key.in(graphicValueFilterDTO.getKeys()))
                .groupBy(
                        Expressions.stringTemplate("TO_CHAR({0}, 'MM')", telemetryDay.date),
                        telemetryDay.key,
                        telemetryDay.station.id
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
