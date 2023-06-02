package com.example.eksamenbackend.Races.model;

import com.example.eksamenbackend.sailboat.model.SailboatModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "race_participant",
            joinColumns = @JoinColumn(name = "race_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id"))
    @EqualsAndHashCode.Exclude
    private Set<ParticipantModel> participantModels = new HashSet<>();
}


