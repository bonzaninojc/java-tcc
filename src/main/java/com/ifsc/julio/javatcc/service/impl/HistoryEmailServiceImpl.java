package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.entity.HistoryEmailEntity;
import com.ifsc.julio.javatcc.repository.HistoryEmailRepository;
import com.ifsc.julio.javatcc.service.HistoryEmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HistoryEmailServiceImpl implements HistoryEmailService {

    private HistoryEmailRepository historyEmailRepository;

    @Override
    public HistoryEmailEntity save(HistoryEmailEntity historyEmailEntity) {
        return historyEmailRepository.save(historyEmailEntity);
    }
}
