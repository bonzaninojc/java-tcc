package com.ifsc.julio.javatcc.config;

import com.ifsc.julio.javatcc.dto.station.StationDTO;
import com.ifsc.julio.javatcc.service.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.*;
import static com.ifsc.julio.javatcc.util.Const.*;

@AllArgsConstructor
@Component
public class DataInitializer {

    private StationService stationService;

    @PostConstruct
    public void initStation() {
        StationDTO station1 = StationDTO.builder()
                .uf("SC")
                .city("Araranguá")
                .address("Av. 15 de novembro")
                .date(new Date())
                .email("julio.bp25@aluno.ifsc.edu.br")
                .phone("48991455898")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("IFSC - Araranguá")
                .ibge("4201406")
                .lat("-28.947756838950696")
                .lng("-49.49327910275754")
                .build();

        StationDTO station2 = StationDTO.builder()
                .uf("SC")
                .city("Tubarão")
                .address("Rua Dep. Olices Pedro de Caldas")
                .date(new Date())
                .email("vinicius.sb2002@aluno.ifsc.edu.br")
                .phone("48984840861")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("IFSC - Tubarão")
                .ibge("4218707")
                .lat("-28.474675405574864")
                .lng("-49.0238219011332")
                .build();

        StationDTO station3 = StationDTO.builder()
                .uf("SC")
                .city("Florianópolis")
                .address("Av. Mauro Ramos")
                .date(new Date())
                .email("vinicius.sb2002@aluno.ifsc.edu.br")
                .phone("48984840861")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("IFSC - Florianópolis")
                .ibge("4205407")
                .lat("-27.59420375421378")
                .lng("-48.543223969795434")
                .build();

        StationDTO station4 = StationDTO.builder()
                .uf("SC")
                .city("Criciúma")
                .address("Av. Mauro Ramos")
                .date(new Date())
                .email("vinicius.sb2002@aluno.ifsc.edu.br")
                .phone("48984840861")
                .requestsPerDay(REQUESTS_DEFAULT)
                .nickname("IFSC - Criciúma")
                .ibge("4204608")
                .lat("-28.677405063518073")
                .lng("-49.331830349733025")
                .build();

        stationService.saveAll(List.of(station1, station2, station3, station4));
    }
}
