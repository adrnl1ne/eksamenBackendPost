package com.example.eksamenbackend.startup;

import com.example.eksamenbackend.service.ParticipantService;
import com.example.eksamenbackend.service.RaceService;
import com.example.eksamenbackend.service.SailboatService;
import com.example.eksamenbackend.utils.RaceCreationUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


@Component
public class RaceCreationStartup {
    private final RaceCreationUtils raceCreationUtils;

    private final
    SailboatService sailboatService;

    private final
    ParticipantService participantService;


    private final
    RaceService raceService;


    public RaceCreationStartup(RaceCreationUtils raceCreationUtils, RaceService raceService,
                               SailboatService sailboatService, ParticipantService participantService) {
        this.raceCreationUtils = raceCreationUtils;
        this.raceService = raceService;
        this.sailboatService = sailboatService;
        this.participantService = participantService;
    }

    @PostConstruct
    public void initializeRaces() {
        raceCreationUtils.createAllRaces();
        sailboatService.initializeBoats();
        participantService.initializeParticipants();
        raceService.saveParticipantsInRacesOnStartup();
    }
}

