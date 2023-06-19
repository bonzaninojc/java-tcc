package com.ifsc.julio.javatcc.repository.impl;

import com.ifsc.julio.javatcc.dto.GraphicValueDTO;
import com.ifsc.julio.javatcc.dto.GraphicValueFilterDTO;
import com.ifsc.julio.javatcc.entity.QDeviceTelemetryDayEntity;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryDayRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.*;
import static com.querydsl.core.types.dsl.Expressions.*;
import static java.util.Objects.*;

public class DeviceTelemetryDayRepositoryCustomImpl implements DeviceTelemetryDayRepositoryCustom {

    private final QDeviceTelemetryDayEntity telemetry = QDeviceTelemetryDayEntity.deviceTelemetryDayEntity;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<GraphicValueDTO> getHumidityByDay(GraphicValueFilterDTO graphicValueFilterDTO) {
        JPQLQuery<GraphicValueDTO> query = new JPAQueryFactory(em)
                .select(
                    Projections.constructor(
                        GraphicValueDTO.class,
                        telemetry.date,
                        telemetry.value
                    )
                )
                .from(telemetry)
                .where(telemetry.key.eq(graphicValueFilterDTO.getKey())
                        .and(telemetry.station.id.eq(graphicValueFilterDTO.getStationId())));

        if (nonNull(graphicValueFilterDTO.getInitDate()) && nonNull(graphicValueFilterDTO.getFinalDate())) {
            query.where(telemetry.date.between(graphicValueFilterDTO.getInitDate(), graphicValueFilterDTO.getFinalDate()));
        }

        return query
                .groupBy(telemetry.date, telemetry.value)
                .fetch();
    }

    @Override
    public List<GraphicValueDTO> getHumidityByMonth(GraphicValueFilterDTO graphicValueFilterDTO) {
        JPQLQuery<GraphicValueDTO> query = new JPAQueryFactory(em)
                .select(
                    Projections.constructor(
                        GraphicValueDTO.class,
                        template(Date.class, "MONTH({0})", telemetry.date),
                        telemetry.value.avg()
                    )
                )
                .from(telemetry)
                .where(telemetry.key.eq(graphicValueFilterDTO.getKey())
                        .and(telemetry.station.id.eq(graphicValueFilterDTO.getStationId())));

        if (nonNull(graphicValueFilterDTO.getInitDate()) && nonNull(graphicValueFilterDTO.getFinalDate())) {
            query.where(telemetry.date.between(graphicValueFilterDTO.getInitDate(), graphicValueFilterDTO.getFinalDate()));
        }

        return query
                .groupBy(template(Date.class, "MONTH({0})", telemetry.date))
                .fetch();
    }
}
