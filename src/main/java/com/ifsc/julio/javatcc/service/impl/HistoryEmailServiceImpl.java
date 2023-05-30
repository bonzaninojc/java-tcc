package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.entity.HistoryEmailEntity;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.repository.HistoryEmailRepository;
import com.ifsc.julio.javatcc.service.HistoryEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryEmailServiceImpl implements HistoryEmailService {

    @Autowired
    private HistoryEmailRepository historyEmailRepository;

    @Override
    public HistoryEmailEntity save(HistoryEmailEntity historyEmailEntity) {
        return historyEmailRepository.save(historyEmailEntity);
    }
}
