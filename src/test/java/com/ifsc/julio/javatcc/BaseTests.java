package com.ifsc.julio.javatcc;

import com.ifsc.julio.javatcc.service.StationService;
import com.ifsc.julio.javatcc.service.impl.StationServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@DataJpaTest(showSql = false)
public class BaseTests {

    @TestConfiguration
    public static class TestConfig {

        @Bean
        StationService stationService() {
            return new StationServiceImpl();
        }

        @Bean
        ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }
}