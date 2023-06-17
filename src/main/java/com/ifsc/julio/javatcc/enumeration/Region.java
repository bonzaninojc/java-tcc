package com.ifsc.julio.javatcc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import static java.util.List.*;

@AllArgsConstructor
@Getter
public enum Region {
    NORTH(of("AC", "AP", "AM", "PA", "RO", "RR", "TO")),
    SOUTH(of("PR", "RS", "SC")),
    SOUTHEAST(of("ES", "MG", "RJ", "SP")),
    NORTHEAST(of("AL", "BA", "CE", "MA", "PB", "PE", "PI", "RN", "SE")),
    MIDWEST(of("DF", "GO", "MT", "MS"));

    private final List<String> states;
}
