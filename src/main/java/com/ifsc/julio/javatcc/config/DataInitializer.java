package com.ifsc.julio.javatcc.config;

import com.ifsc.julio.javatcc.dto.StationDTO;
import com.ifsc.julio.javatcc.service.StationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import static com.ifsc.julio.javatcc.util.Const.*;

@Component
public class DataInitializer {

    @Autowired
    private StationService stationService;

    @PostConstruct
    public void initStation() {
        StationDTO stationTubarao = StationDTO.builder()
                .uf("SC")
                .city("Tubarão")
                .address("IFSC")
                .date(new Date())
                .email("julio.bp25@aluno.ifsc.edu.br")
                .phone("48991455898")
                .requestsPerDay(REQUESTS_DEFAULT)
                .build();

        StationDTO stationCapivariDeBaixo = StationDTO.builder()
                .uf("SC")
                .city("Capivari De Baixo")
                .address("IFSC")
                .date(new Date())
                .email("vinicius.sb2002@aluno.ifsc.edu.br")
                .phone("48984840861")
                .requestsPerDay(REQUESTS_DEFAULT)
                .build();
        stationService.saveAll(List.of(stationTubarao, stationCapivariDeBaixo));
    }
}
