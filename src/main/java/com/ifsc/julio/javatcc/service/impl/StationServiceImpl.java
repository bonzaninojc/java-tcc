package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.dto.DisableStationDTO;
import com.ifsc.julio.javatcc.dto.StationDTO;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.exception.StationException;
import com.ifsc.julio.javatcc.repository.StationRepository;
import com.ifsc.julio.javatcc.service.StationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.ifsc.julio.javatcc.util.Const.REQUESTS_DEFAULT;
import static java.util.Objects.*;
import static java.util.stream.Collectors.toList;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StationDTO save(StationDTO stationDTO) {
        StationEntity stationEntity = modelMapper.map(stationDTO, StationEntity.class);
        stationEntity.setDate(new Date());
        if (isNull(stationEntity.getRequestsPerDay())) {
            stationEntity.setRequestsPerDay(REQUESTS_DEFAULT);
        }
        return modelMapper.map(stationRepository.save(stationEntity), StationDTO.class);
    }

    @Override
    public StationDTO update(StationDTO stationDTO) throws StationException {
        if (isNull(stationDTO.getId())) {
            throw new StationException("Identificador da Estação não informado.");
        }
        Optional<StationEntity> stationEntityOptional = stationRepository.findById(stationDTO.getId());
        if (stationEntityOptional.isEmpty()) {
            throw new StationException("Estação não encontrada.");
        }
        StationEntity stationEntity = stationEntityOptional.get();
        stationEntity.update(stationDTO);
        return modelMapper.map(stationRepository.save(stationEntity), StationDTO.class);
    }

    @Override
    public void disable(DisableStationDTO disableStationDTO) throws StationException {
        if (isNull(disableStationDTO.getStationId())) {
            throw new StationException("Identificador da Estação não informado.");
        }
        Optional<StationEntity> stationEntityOptional = stationRepository.findById(disableStationDTO.getStationId());
        if (stationEntityOptional.isEmpty()) {
            throw new StationException("Estação não encontrada.");
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
    public StationEntity findById(UUID stationId) {
        return stationRepository.findById(stationId).orElse(null);
    }

    @Override
    public List<StationDTO> findAll() {
        return stationRepository.findAll()
                .stream()
                .map(station -> modelMapper.map(station, StationDTO.class))
                .collect(toList());
    }
}
