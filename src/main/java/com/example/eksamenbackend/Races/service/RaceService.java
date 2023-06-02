package com.example.eksamenbackend.Races.service;

import com.example.eksamenbackend.Races.model.ParticipantModel;
import com.example.eksamenbackend.Races.model.RaceModel;
import com.example.eksamenbackend.Races.repository.ParticipantRepository;
import com.example.eksamenbackend.Races.repository.RaceRepository;
import com.example.eksamenbackend.sailboat.model.SailboatModel;
import com.example.eksamenbackend.utils.RaceCreationUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class RaceService {
    @Autowired
    RaceRepository raceRepository;
    @Autowired
    ParticipantRepository participantRepository;

    public RaceService(RaceRepository raceRepository, ParticipantRepository participantRepository) {
        this.raceRepository = raceRepository;
        this.participantRepository = participantRepository;
    }

    public Optional<RaceModel> findById(Integer id) {
        return raceRepository.findById(id);
    }

    public List<ParticipantModel> findParticipantsByRaceId(Integer id) {
        Optional<RaceModel> race = raceRepository.findById(id);
        if (race.isPresent()) {
            return participantRepository.findParticipantModelsByRaceId(race.get().getId());
        }
        return Collections.emptyList();
    }

    @PostConstruct
    public void initializeRaces() {
        // Check if there are already races in the database
        if (raceRepository.count() == 0) {
            LocalDate startDate = LocalDate.of(2023, 5, 1);
            LocalDate endDate = LocalDate.of(2023, 10, 1);
            List<LocalDate> wednesdays = RaceCreationUtils.getWednesdaysBetween(startDate, endDate);

            // Create races for each boat type and Wednesday
            SailboatModel.SailboatType[] boatTypes = SailboatModel.SailboatType.values();
            for (LocalDate wednesday : wednesdays) {
                for (SailboatModel.SailboatType boatType : boatTypes) {
                    createRace(boatType, wednesday);
                }
            }
        }
    }
    public RaceModel createRace(SailboatModel.SailboatType raceType, LocalDate racedate) {
        RaceModel race = new RaceModel();
        race.setRaceType(raceType);
        race.setDate(racedate);
        System.out.println(racedate);
        return raceRepository.save(race);
    }

    public List<ParticipantModel> findParticipantsByBoatType(SailboatModel.SailboatType boatType) {
        System.out.println(boatType);
        return participantRepository.findParticipantModelsByBoatType(boatType);
    }

    public void saveParticipants(List<ParticipantModel> participants) {
        System.out.println(participants);
        participantRepository.saveAll(participants);
    }
}
