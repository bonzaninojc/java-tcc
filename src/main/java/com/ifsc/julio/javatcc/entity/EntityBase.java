package com.ifsc.julio.javatcc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "base")
public class EntityBase {

    @Id
    @GeneratedValue
    private UUID id;
}
