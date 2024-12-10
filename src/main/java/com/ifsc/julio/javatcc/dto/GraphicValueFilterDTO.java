package com.ifsc.julio.javatcc.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class GraphicValueFilterDTO {
    private List<UUID> stationIds;
    private List<String> keys;
    private String average;
    private LocalDate initDate;
    private LocalDate finalDate;
}
