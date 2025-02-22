package com.ifsc.julio.javatcc.dto.thingsboard;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class LoginDTO {
    private String username;
    private String password;
}
