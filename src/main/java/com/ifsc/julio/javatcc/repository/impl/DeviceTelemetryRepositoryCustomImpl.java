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
            .append("      AVG(value) AS average, ")
            .append("      DATE_TRUNC('hour', date) AS hour, ")
            .append("      key AS key ")
            .append(" FROM device_telemetry ")
            .append(" WHERE date BETWEEN :initDate AND :finalDate ")
            .append(" AND key = :key ")
            .append(" GROUP BY hour, key ");

        List<Tuple> results = em.createNativeQuery(jpql.toString(), Tuple.class)
                .setParameter("initDate", dto.getInitDate())
                .setParameter("finalDate", dto.getFinalDate())
                .setParameter("key", dto.getKey())
                .getResultList();

        List<DeviceTelemetryHourDTO> devices = new ArrayList<>();
        results.forEach(device -> {
            devices.add(DeviceTelemetryHourDTO.builder()
                    .average((Double) device.get("average"))
                    .key((String) device.get("key"))
                    .hour((Date) device.get("hour"))
                    .build());
        });

        return devices;
    }

    @Override
    public List<DeviceTelemetryDayDTO> getDayAverage(AverageDTO dto) {
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT ")
            .append("      AVG(value) AS average, ")
            .append("      DATE_TRUNC('day', date) AS day, ")
            .append("      key AS key ")
            .append(" FROM device_telemetry ")
            .append(" WHERE date BETWEEN :initDate AND :finalDate ")
            .append(" AND key = :key ")
            .append(" GROUP BY day, key ");

        List<Tuple> results = em.createNativeQuery(jpql.toString(), Tuple.class)
                .setParameter("initDate", dto.getInitDate())
                .setParameter("finalDate", dto.getFinalDate())
                .setParameter("key", dto.getKey())
                .getResultList();

        List<DeviceTelemetryDayDTO> devices = new ArrayList<>();
        results.forEach(device -> {
            devices.add(DeviceTelemetryDayDTO.builder()
                    .average((Double) device.get("average"))
                    .key((String) device.get("key"))
                    .day((Date) device.get("day"))
                    .build());
        });

        return devices;
    }
}
