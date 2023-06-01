package com.example.eksamenbackend.Races.service;

import com.example.eksamenbackend.Races.model.ParticipantModel;
import com.example.eksamenbackend.Races.model.RaceModel;
import com.example.eksamenbackend.Races.repository.ParticipantRepository;
import com.example.eksamenbackend.Races.repository.RaceRepository;
import com.example.eksamenbackend.sailboat.model.SailboatModel;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class RaceService {

    private final RaceRepository raceRepository;
    private final ParticipantRepository participantRepository;

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
            // Create races for each boat type
            createRace(SailboatModel.SailboatType.FOOT_40);
            createRace(SailboatModel.SailboatType.FOOT_25);
            createRace(SailboatModel.SailboatType.FOOT_25_40);
        }
    }

    private void createRace(SailboatModel.SailboatType raceType) {
        RaceModel race = new RaceModel();
        race.setRaceType(raceType);
        raceRepository.save(race);
    }
}
