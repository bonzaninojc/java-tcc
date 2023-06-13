package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.entity.LostDataEntity;
import com.ifsc.julio.javatcc.repository.LostDataRepository;
import com.ifsc.julio.javatcc.service.LostDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class LostDataServiceImpl implements LostDataService {

    @Autowired
    private LostDataRepository repository;

    @Override
    public List<LostDataEntity> findByStationId(UUID stationId) {
        return repository.findByStationId(stationId);
    }

    @Override
    public LostDataEntity save(LostDataEntity lostDataEntity) {
        return repository.save(lostDataEntity);
    }

    @Override
    public void delete(LostDataEntity lostDataEntity) {
        repository.delete(lostDataEntity);
    }
}
