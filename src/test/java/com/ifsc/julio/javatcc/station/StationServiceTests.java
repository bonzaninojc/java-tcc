package com.ifsc.julio.javatcc.station;

import com.ifsc.julio.javatcc.BaseTests;
import com.ifsc.julio.javatcc.dto.StationDTO;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.service.StationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import static com.ifsc.julio.javatcc.util.Const.REQUESTS_DEFAULT;
import static io.jsonwebtoken.lang.Assert.*;

@SpringJUnitConfig(BaseTests.TestConfig.class)
@RunWith(SpringRunner.class)
public class StationServiceTests extends BaseTests {

    @Autowired
    private StationService stationService;

    @Test
    public void stationNonNullTest() {
        StationEntity station = stationService.save(StationDTO.builder()
                .uf("SC")
                .city("Tubarão")
                .address("IFSC")
                .date(new Date())
                .email("julio.bp25@aluno.ifsc.edu.br")
                .phone("48991455898")
                .requestsPerDay(REQUESTS_DEFAULT)
                .build());
        notNull(station.getId());
    }
}
