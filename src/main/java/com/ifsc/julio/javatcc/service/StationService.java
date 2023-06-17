package com.ifsc.julio.javatcc.service;

import com.ifsc.julio.javatcc.dto.*;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.exception.StationException;
import java.util.List;
import java.util.UUID;

public interface StationService {

    StationDTO save(StationDTO stationDTO);
    StationDTO update(StationDTO stationDTO) throws StationException;
    void saveAll(List<StationDTO> stations);
    StationEntity findById(UUID stationId);
    List<StationDTO> findAll();
    void disable(DisableStationDTO disableStationDTO) throws StationException;
    List<StationDTO> findAllWithFilters(FiltroStationDTO filtroStationDTO);
}
