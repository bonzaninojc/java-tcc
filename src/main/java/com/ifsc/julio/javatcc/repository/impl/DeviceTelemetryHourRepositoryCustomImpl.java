package com.ifsc.julio.javatcc.repository.impl;

import com.ifsc.julio.javatcc.repository.DeviceTelemetryHourRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class DeviceTelemetryHourRepositoryCustomImpl implements DeviceTelemetryHourRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean hasPassedThreeHoursSinceLimitDate(UUID station) {
        Date limitDate = getLimitDate();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ")
           .append("    COUNT(*) ")
           .append(" FROM device_telemetry_hour ")
           .append(" WHERE date >= :limitDate ")
           .append(" AND device_telemetry_hour.id_station = :station ");

        Long count = (Long) em.createNativeQuery(sql.toString())
                .setParameter("limitDate", limitDate)
                .setParameter("station", station)
                .getSingleResult();

        return count.equals(0L);
    }

    private Date getLimitDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -3);
        return calendar.getTime();
    }
}
