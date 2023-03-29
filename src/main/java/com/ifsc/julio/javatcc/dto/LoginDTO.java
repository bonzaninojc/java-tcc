package com.ifsc.julio.javatcc.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class LoginDTO {
    private String username;
    private String password;
}
