package com.ifsc.julio.javatcc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name = "history_email")
public class HistoryEmailEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String text;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_station")
    private StationEntity station;
}
