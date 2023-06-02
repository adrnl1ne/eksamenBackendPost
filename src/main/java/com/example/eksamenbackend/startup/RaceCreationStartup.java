package com.example.eksamenbackend.startup;

import com.example.eksamenbackend.Races.service.ParticipantService;
import com.example.eksamenbackend.Races.service.RaceService;
import com.example.eksamenbackend.sailboat.service.SailboatService;
import com.example.eksamenbackend.utils.RaceCreationUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RaceCreationStartup {
    private final RaceCreationUtils raceCreationUtils;

    @Autowired
    SailboatService sailboatService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    RaceService raceService;


    public RaceCreationStartup(RaceCreationUtils raceCreationUtils) {
        this.raceCreationUtils = raceCreationUtils;
    }

    @PostConstruct
    public void initializeRaces() {
        raceCreationUtils.createAllRaces();
        sailboatService.initializeBoats();
        participantService.initializeParticipants();
        raceService.saveParticipantsInRacesOnStartup();
    }
}

