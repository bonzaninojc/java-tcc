package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.dto.FiltroStationDTO;
import com.ifsc.julio.javatcc.entity.StationEntity;
import java.util.List;

public interface StationRepositoryCustom {

    List<StationEntity> findAllWithFilters(FiltroStationDTO filtroStationDTO);
}
