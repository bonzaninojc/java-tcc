package com.ifsc.julio.javatcc.repository.impl;

import com.ifsc.julio.javatcc.dto.DeviceTelemetryDayDto;
import com.ifsc.julio.javatcc.repository.DeviceTelemetryRepositoryCustom;
import jakarta.persistence.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.stream.Collectors;

public class DeviceTelemetryRepositoryCustomImpl implements DeviceTelemetryRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void getMedia() {

        String sql = "SELECT AVG(value) as average, DATE_TRUNC('day', date) AS day FROM device_telemetry GROUP BY day;";
        List<Object[]> results = em.createNativeQuery(sql).getResultList();

        List<DeviceTelemetryDayDto> dtos = results.stream()
                .map(result -> modelMapper.map(result, DeviceTelemetryDayDto.class))
                .collect(Collectors.toList());

    }
}
