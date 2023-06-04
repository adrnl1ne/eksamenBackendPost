package com.example.eksamenbackend.startup;

import com.example.eksamenbackend.service.ParticipantService;
import com.example.eksamenbackend.service.RaceService;
import com.example.eksamenbackend.service.SailboatService;
import com.example.eksamenbackend.utils.RaceCreationUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RaceCreationStartup {

    @Autowired
    RaceCreationUtils raceCreationUtils;
    @Autowired
    SailboatService sailboatService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    RaceService raceService;


    public RaceCreationStartup(RaceCreationUtils raceCreationUtils, RaceService raceService,
                               SailboatService sailboatService, ParticipantService participantService) {
        this.raceCreationUtils = raceCreationUtils;
        this.raceService = raceService;
        this.sailboatService = sailboatService;
        this.participantService = participantService;
    }

    //As the backend is run, the postconstruct will run the following methods
    //after the application context has been loaded
    @PostConstruct
    public void initializeRaces() {
        raceCreationUtils.createAllRaces();
        sailboatService.initializeBoats();
        participantService.initializeParticipants();
        raceService.saveRacesInParticipant();
    }
}

