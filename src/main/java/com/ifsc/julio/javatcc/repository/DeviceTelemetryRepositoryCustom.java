package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.dto.DeviceTelemetryDayDTO;
import com.ifsc.julio.javatcc.dto.DeviceTelemetryHourDTO;
import com.ifsc.julio.javatcc.dto.AverageDTO;
import java.util.List;
import java.util.UUID;

public interface DeviceTelemetryRepositoryCustom {

    List<DeviceTelemetryHourDTO> getHourAverage(AverageDTO dto);
    List<DeviceTelemetryDayDTO> getDayAverage(AverageDTO dto);
    boolean hasPassedThreeHoursSinceLimitDate(UUID station);
}
