package com.ifsc.julio.javatcc.service;

import com.ifsc.julio.javatcc.entity.LostDataEntity;
import java.util.List;
import java.util.UUID;

public interface LostDataService {
    List<LostDataEntity> findByStationId(UUID stationId);
    LostDataEntity save(LostDataEntity lostDataEntity);
    void delete(LostDataEntity lostDataEntity);
}
