package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.dto.query.*;
import java.util.List;

public interface DeviceTelemetryRepositoryCustom {

    List<DailyAverageDTO> getDailyAverages(AverageSearchDTO averageSearch);
    List<WeeklyAverageDTO> getWeeklyAverages(AverageSearchDTO averageSearch);
    List<MonthlyAverageDTO> getMonthlyAverages(AverageSearchDTO averageSearch);
}
