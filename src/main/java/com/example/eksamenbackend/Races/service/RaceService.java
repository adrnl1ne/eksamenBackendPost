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
import java.util.*;


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

    /*public void simulateRacesOnStartup() {
        System.out.println("test");
        List<RaceModel> races = raceRepository.findAll();
        simulateRaces(races);
    }*/

    public void saveParticipantsInRacesOnStartup() {
        List<RaceModel> races = raceRepository.findAll();

        Random random = new Random();

        for (RaceModel race : races) {
            // Retrieve participants for the race
            List<ParticipantModel> participants = participantRepository.findParticipantModelsByRaceId(race.getId());

            // Shuffle the participants randomly
            Collections.shuffle(participants, random);

            // Determine the number of starting boats
            int startingBoats = participants.size();

            int placement = 1;
            for (ParticipantModel participant : participants) {
                // Assign points based on placement
                participant.setPoints(placement);

                // Assign special conditions
                if (placement <= 2) {
                    // First and second place cannot have not completed or not started
                    participant.setNotCompleted(false);
                    participant.setNotStarted(false);
                } else {
                    // Randomly assign special conditions
                    boolean notCompleted = random.nextBoolean();
                    boolean notStarted = random.nextBoolean();

                    participant.setNotCompleted(notCompleted);
                    participant.setNotStarted(notStarted);

                    if (notCompleted) {
                        // Set points to the amount of starting boats + 1
                        participant.setPoints(startingBoats + 1);
                    } else if (notStarted) {
                        // Set points to the amount of starting boats + 2
                        participant.setPoints(startingBoats + 2);
                    }
                }

                // Save the updated participant
                participantRepository.save(participant);

                placement++;
            }

            // Save the updated race with participants
            race.setParticipants(new HashSet<>(participants));
            raceRepository.save(race);
        }
    }


}
