package com.example.eksamenbackend.utils;

import com.example.eksamenbackend.Races.model.ParticipantModel;
import com.example.eksamenbackend.Races.model.RaceModel;
import com.example.eksamenbackend.Races.service.RaceService;
import com.example.eksamenbackend.sailboat.model.SailboatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RaceCreationUtils {

    @Autowired
    RaceService raceService;

    public RaceCreationUtils(RaceService raceService) {
        this.raceService = raceService;
    }


    public static List<LocalDate> getWednesdaysBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> wednesdays = new ArrayList<>();
        LocalDate current = startDate;

        while (current.isBefore(endDate) || current.isEqual(endDate)) {
            if (current.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                wednesdays.add(current);
            }
            current = current.plusDays(1);
        }

        return wednesdays;
    }

    public void createAllRaces() {
        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 1);
        List<LocalDate> wednesdays = getWednesdaysBetween(startDate, endDate);

        // Create race for each Wednesday
        for (LocalDate wednesday : wednesdays) {
            createRacesForWednesday(wednesday);
        }
    }

    public void createRacesForWednesday(LocalDate localDate) {
        SailboatModel.SailboatType[] boatTypes = SailboatModel.SailboatType.values();

        for (SailboatModel.SailboatType boatType : boatTypes) {

            raceService.createRace(boatType, localDate);

            List<ParticipantModel> participants = raceService.findParticipantsByBoatType(boatType);

            // Assign points to participants based on race results
            int points = 1;
            for (ParticipantModel participant : participants) {
                participant.setPoints(points);
                points++;
            }

            // Save the updated participants in the database
            raceService.saveParticipants(participants);
        }
    }




}

