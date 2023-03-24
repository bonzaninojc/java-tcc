package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.dto.query.*;
import java.util.List;

public interface DeviceTelemetryRepositoryCustom {

    List<AverageDTO> getAverage(AverageSearchDTO averageSearch);
}
