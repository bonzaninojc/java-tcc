package com.ifsc.julio.javatcc.service;

import com.ifsc.julio.javatcc.dto.station.FiltroStationDTO;
import com.ifsc.julio.javatcc.dto.station.StationDTO;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.exception.StationException;
import java.util.List;
import java.util.UUID;

public interface StationService {

    StationDTO save(StationDTO stationDTO);
    StationDTO update(StationDTO stationDTO) throws StationException;
    List<StationDTO> saveAll(List<StationDTO> stations);
    StationEntity findById(UUID stationId);
    StationDTO findByIdDTO(UUID stationId);
    List<StationDTO> findAll();
    void disable(UUID stationId);
    void enable(UUID stationId);
    List<StationDTO> findAllWithFilters(FiltroStationDTO filtroStationDTO);
}
