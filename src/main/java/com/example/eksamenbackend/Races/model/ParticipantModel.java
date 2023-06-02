package com.example.eksamenbackend.Races.model;

import com.example.eksamenbackend.sailboat.model.SailboatModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class ParticipantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int points;

    private boolean notCompleted;
    private boolean notStarted;

    @Column(name = "boat_id")
    private int boatId;

    @Column(name = "boat_name")
    private String boatName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "boat_id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private SailboatModel boat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    @EqualsAndHashCode.Exclude
    private RaceModel race;

    @Column(name = "boat_type")
    @Enumerated(EnumType.STRING)
    private SailboatModel.SailboatType boatType;

}





