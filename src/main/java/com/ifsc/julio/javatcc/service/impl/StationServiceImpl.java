package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.dto.DisableStationDTO;
import com.ifsc.julio.javatcc.dto.StationDTO;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.repository.StationRepository;
import com.ifsc.julio.javatcc.service.StationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static com.ifsc.julio.javatcc.util.Const.REQUESTS_DEFAULT;
import static java.util.Objects.*;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StationEntity save(StationDTO stationDTO) {
        StationEntity stationEntity = modelMapper.map(stationDTO, StationEntity.class);
        stationEntity.setDate(new Date());
        if (isNull(stationEntity.getRequestsPerDay())) {
            stationEntity.setRequestsPerDay(REQUESTS_DEFAULT);
        }
        return stationRepository.save(stationEntity);
    }

    @Override
    public StationEntity update(StationDTO stationDTO) {
        if (isNull(stationDTO.getId())) {
            throw new RuntimeException("Identificador da Estação não informado.");
        }
        Optional<StationEntity> stationEntityOptional = stationRepository.findById(stationDTO.getId());
        if (stationEntityOptional.isEmpty()) {
            throw new RuntimeException("Estação não encontrada.");
        }
        StationEntity stationEntity = stationEntityOptional.get();
        stationEntity.update(stationDTO);
        return stationRepository.save(stationEntity);
    }

    @Override
    public void disable(DisableStationDTO disableStationDTO) {
        if (isNull(disableStationDTO.getStationId())) {
            throw new RuntimeException("Identificador da Estação não informado.");
        }
        Optional<StationEntity> stationEntityOptional = stationRepository.findById(disableStationDTO.getStationId());
        if (stationEntityOptional.isEmpty()) {
            throw new RuntimeException("Estação não encontrada.");
        }
        StationEntity stationEntity = stationEntityOptional.get();
        stationEntity.setDisabled(disableStationDTO.isDisabled());
        stationRepository.save(stationEntity);
    }

    @Override
    public void saveAll(List<StationDTO> stations) {
        stationRepository.saveAll(stations.stream()
                .map(station -> modelMapper.map(station, StationEntity.class))
                .toList());
    }

    @Override
    public List<StationEntity> findAll() {
        return stationRepository.findAll();
    }
}
