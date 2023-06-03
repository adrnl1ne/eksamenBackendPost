package com.example.eksamenbackend.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;


@Entity
@Data
public class RaceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private SailboatModel.SailboatType raceType;

}


