package com.example.eksamenbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@JsonIgnoreProperties("boat")
public class ParticipantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int points;

    private boolean Completed;

    private boolean Started;

    @Column(name = "boat_id")
    private int boatId;

    @Column(name = "boat_name")
    private String boatName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "boat_id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private SailboatModel boat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "race_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private RaceModel race;

    @Column(name = "boat_type")
    @Enumerated(EnumType.STRING)
    private SailboatModel.SailboatType boatType;

}





