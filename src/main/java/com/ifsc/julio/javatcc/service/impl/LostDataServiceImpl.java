package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.repository.LostDataRepository;
import com.ifsc.julio.javatcc.service.LostDataService;
import org.springframework.beans.factory.annotation.Autowired;

public class LostDataServiceImpl implements LostDataService {

    @Autowired
    private LostDataRepository repository;
}
