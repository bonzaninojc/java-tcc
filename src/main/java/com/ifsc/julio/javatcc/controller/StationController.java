package com.ifsc.julio.javatcc.controller;

import com.ifsc.julio.javatcc.dto.DisableStationDTO;
import com.ifsc.julio.javatcc.dto.StationDTO;
import com.ifsc.julio.javatcc.service.StationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationService stationService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("teste")
    public void teste() {
    }

    @PostMapping("save")
    public StationDTO save(@RequestBody StationDTO stationDTO) {
        return modelMapper.map(stationService.save(stationDTO), StationDTO.class);
    }

    @PutMapping("/update")
    public StationDTO update(@RequestBody StationDTO stationDTO) {
        return modelMapper.map(stationService.update(stationDTO), StationDTO.class);
    }

    @PutMapping("/disable")
    public void disable(@RequestBody DisableStationDTO disableStationDTO) {
       stationService.disable(disableStationDTO);
    }

    @GetMapping
    public List<StationDTO> findAll() {
        return stationService.findAll()
                .stream()
                .map(station -> modelMapper.map(station, StationDTO.class))
                .collect(toList());
    }
}
