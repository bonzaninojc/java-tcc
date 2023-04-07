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
            .append(" new com.ifsc.julio.javatcc.dto.DeviceTelemetryHourDTO( ")
            .append("    AVG(value) as average, ")
            .append("    DATE_TRUNC('hour', date) AS hour, ")
            .append("    key, ")
            .append("    station ")
            .append(" ) ")
            .append(" FROM DeviceTelemetryEntity ")
            .append(" WHERE date BETWEEN :initDate AND :dataFinal ")
            .append(" AND key = :key ")
            .append(" AND station = :station ")
            .append(" GROUP BY hour ");

        return em.createQuery(jpql.toString(), DeviceTelemetryHourDTO.class)
                .setParameter("initDate", dto.getInitDate())
                .setParameter("finalDate", dto.getFinalDate())
                .setParameter("key", dto.getKey())
                .setParameter("station", dto.getStation())
                .getResultList();
    }

    @Override
    public List<DeviceTelemetryDayDTO> getDayAverage(AverageDTO dto) {
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT ")
            .append(" new com.ifsc.julio.javatcc.dto.DeviceTelemetryDayDTO( ")
            .append("    AVG(value) as average, ")
            .append("    DATE_TRUNC('day', date) AS day, ")
            .append("    key, ")
            .append("    station ")
            .append(" ) ")
            .append(" FROM DeviceTelemetryEntity ")
            .append(" WHERE date BETWEEN :initDate AND :dataFinal ")
            .append(" AND key = :key ")
            .append(" AND station = :station ")
            .append(" GROUP BY day ");

        return em.createQuery(jpql.toString(), DeviceTelemetryDayDTO.class)
                .setParameter("initDate", dto.getInitDate())
                .setParameter("finalDate", dto.getFinalDate())
                .setParameter("key", dto.getKey())
                .setParameter("station", dto.getStation())
                .getResultList();
    }
}
