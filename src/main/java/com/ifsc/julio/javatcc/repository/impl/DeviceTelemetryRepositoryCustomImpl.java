package com.ifsc.julio.javatcc.repository.impl;

import com.ifsc.julio.javatcc.dto.DeviceTelemetryDayDTO;
import com.ifsc.julio.javatcc.dto.DeviceTelemetryHourDTO;
import com.ifsc.julio.javatcc.dto.AverageDTO;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryRepositoryCustom;
import jakarta.persistence.*;
import java.util.*;

public class DeviceTelemetryRepositoryCustomImpl implements DeviceTelemetryRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DeviceTelemetryHourDTO> getHourAverage(AverageDTO dto) {
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT ")
            .append("      AVG(value) as average, ")
            .append("      DATE_TRUNC('hour', date) AS hour, ")
            .append("      key ")
            .append(" FROM device_telemetry ")
            .append(" WHERE date BETWEEN :initDate AND :finalDate ")
            .append(" AND key = :key ")
            .append(" GROUP BY hour, key ");

        List<DeviceTelemetryHourDTO> result = em.createNativeQuery(jpql.toString())
                .setParameter("initDate", dto.getInitDate())
                .setParameter("finalDate", dto.getFinalDate())
                .setParameter("key", dto.getKey())
                .getResultList();
        return result;
    }

    @Override
    public List<DeviceTelemetryDayDTO> getDayAverage(AverageDTO dto) {
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT ")
            .append("      AVG(value) as average, ")
            .append("      DATE_TRUNC('day', date) AS day, ")
            .append("      key AS key ")
            .append(" FROM device_telemetry ")
            .append(" WHERE date BETWEEN :initDate AND :finalDate ")
            .append(" AND key = :key ")
            .append(" GROUP BY day, key ");

        //TODO - Resolver

        List<DeviceTelemetryDayDTO> result = em.createNativeQuery(jpql.toString(), DeviceTelemetryDayDTO.class)
                .setParameter("initDate", dto.getInitDate())
                .setParameter("finalDate", dto.getFinalDate())
                .setParameter("key", dto.getKey())
                .getResultList();
        return result;
    }
}
