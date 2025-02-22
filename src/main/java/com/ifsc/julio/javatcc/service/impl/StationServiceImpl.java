package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.dto.station.FiltroStationDTO;
import com.ifsc.julio.javatcc.dto.station.StationDTO;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.exception.StationException;
import com.ifsc.julio.javatcc.repository.StationRepository;
import com.ifsc.julio.javatcc.service.StationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static com.ifsc.julio.javatcc.util.Const.REQUESTS_DEFAULT;
import static java.util.Objects.*;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class StationServiceImpl implements StationService {

    private StationRepository stationRepository;
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
        StationEntity stationEntity = findById(stationDTO.getId());
        stationEntity.update(stationDTO);
        return modelMapper.map(stationRepository.save(stationEntity), StationDTO.class);
    }

    @Override
    public void disable(UUID stationId) throws StationException {
        if (isNull(stationId)) {
            throw new StationException("Identificador da Estação não informado.");
        }
        StationEntity stationEntity = findById(stationId);
        stationEntity.setDisabled(true);
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
        return stationRepository.findById(stationId).orElseThrow(() -> new StationException("Estação não encontrada."));
    }

    @Override
    public StationDTO findByIdDTO(UUID stationId) {
        return modelMapper.map(findById(stationId), StationDTO.class);
    }

    @Override
    public List<StationDTO> findAll() {
        return stationRepository.findAll()
                .stream()
                .map(station -> modelMapper.map(station, StationDTO.class))
                .collect(toList());
    }

    @Override
    public List<StationDTO> findAllWithFilters(FiltroStationDTO filtroStationDTO) {
        return stationRepository.findAllWithFilters(filtroStationDTO)
                .stream()
                .map(station -> modelMapper.map(station, StationDTO.class))
                .collect(toList());
    }
}
