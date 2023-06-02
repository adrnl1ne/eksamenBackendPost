package com.example.eksamenbackend.startup;

import com.example.eksamenbackend.utils.RaceCreationUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


@Component
public class RaceCreationStartup {
    private final RaceCreationUtils raceCreationUtils;

    public RaceCreationStartup(RaceCreationUtils raceCreationUtils) {
        this.raceCreationUtils = raceCreationUtils;
    }

    @PostConstruct
    public void initializeRaces() {
        raceCreationUtils.createAllRaces();
    }
}

