package com.ifsc.julio.javatcc.service;

import com.ifsc.julio.javatcc.dto.DisableStationDTO;
import com.ifsc.julio.javatcc.dto.StationDTO;
import com.ifsc.julio.javatcc.entity.StationEntity;
import java.util.List;
import java.util.UUID;

public interface StationService {

    StationEntity save(StationDTO stationDTO);
    StationEntity update(StationDTO stationDTO);
    void saveAll(List<StationDTO> stations);
    StationEntity findById(UUID stationId);
    List<StationEntity> findAll();
    void disable(DisableStationDTO disableStationDTO);
}
