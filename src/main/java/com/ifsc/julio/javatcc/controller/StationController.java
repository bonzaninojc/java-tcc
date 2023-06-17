package com.ifsc.julio.javatcc.controller;

import com.ifsc.julio.javatcc.dto.*;
import com.ifsc.julio.javatcc.exception.StationException;
import com.ifsc.julio.javatcc.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/station")
@Tag(name = "Estação")
public class StationController {

    @Autowired
    private StationService stationService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(
        description = "Método para gerar novas estações. Após executá-lo, pegar o UUID gerado e configurar na estação localmente."
    )
    @PostMapping("save")
    public StationDTO save(@RequestBody StationDTO stationDTO) {
        return stationService.save(stationDTO);
    }

    @PutMapping("/update")
    public StationDTO update(@RequestBody StationDTO stationDTO) throws StationException {
        return stationService.update(stationDTO);
    }

    @PutMapping("/disable")
    public void disable(@RequestBody DisableStationDTO disableStationDTO) throws StationException {
       stationService.disable(disableStationDTO);
    }

    @GetMapping
    public List<StationDTO> findAll() {
        return stationService.findAll();
    }

    @PostMapping("find-all-filter")
    public List<StationDTO> findAllFilter(@RequestBody FiltroStationDTO filtroStationDTO) {
        return stationService.findAllWithFilters(filtroStationDTO);
    }
}
