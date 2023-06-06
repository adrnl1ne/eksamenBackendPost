package com.example.eksamenbackend.service;


import com.example.eksamenbackend.model.ParticipantModel;
import com.example.eksamenbackend.model.RaceModel;
import com.example.eksamenbackend.repository.ParticipantRepository;
import com.example.eksamenbackend.repository.RaceRepository;
import com.example.eksamenbackend.model.SailboatModel;
import com.example.eksamenbackend.repository.SailboatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParticipantService {

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    SailboatRepository sailboatRepository;

    @Autowired
    RaceRepository raceRepository;

    public ParticipantService(ParticipantRepository participantRepository, SailboatRepository sailboatRepository,
                              RaceRepository raceRepository) {
        this.participantRepository = participantRepository;
        this.sailboatRepository = sailboatRepository;
        this.raceRepository = raceRepository;
    }


    public Set<ParticipantModel> findAll() {
        return new HashSet<>(participantRepository.findAll());
    }

    public List<SailboatModel> findBoatsByParticipantId(Integer id) {
        return sailboatRepository.findBoatModelsById(id);
    }

    public ParticipantModel save(ParticipantModel object) {
        return participantRepository.save(object);
    }

    public void deleteById(Integer integer) {
        participantRepository.deleteById(integer);
    }

    public Optional<ParticipantModel> findById(Integer integer) {
        return participantRepository.findById(integer);
    }

    public Optional<ParticipantModel> findByBoatId(Integer id) {
        return participantRepository.findByBoatId(id);
    }


    // This method checks if there are any participants in the database. If not, it generates 10 random participants.
    public void initializeParticipants() {
        if (participantRepository.count() == 0) {
            generateRandomParticipants();
        }
    }

    // This method generates 10 random participants, and is called in the method above.
    private void generateRandomParticipants() {
        Random random = new Random();

        List<RaceModel> races = raceRepository.findAll();

        for (int i = 0; i < 10; i++) {
            SailboatModel sailboat = getRandomAvailableBoat(random);
            if (sailboat != null) {
                RaceModel matchingRace = findMatchingRace(sailboat.getType(), races);
                if (matchingRace != null) {
                    ParticipantModel participant = new ParticipantModel();
                    participant.setBoatId(sailboat.getId());
                    participant.setBoatName(sailboat.getName());
                    participant.setBoatType(sailboat.getType());
                    participant.setRace(matchingRace);

                    save(participant);

                    sailboat.setAssigned(true);
                    sailboatRepository.save(sailboat);
                }
            }
        }
    }

    // This method find a race that has the same type as the sailboat the participant contains.
    private RaceModel findMatchingRace(SailboatModel.SailboatType sailboatType, List<RaceModel> races) {
        for (RaceModel race : races) {
            if (race.getRaceType() == sailboatType) {
                return race;
            }
        }
        return null;
    }


    // This method finds a random available boat and is used to generate the participant.
    // So that there won't be any participants with the same boat.
    private SailboatModel getRandomAvailableBoat(Random random) {
        List<SailboatModel> availableBoats = sailboatRepository.findByAssigned(false);
        if (!availableBoats.isEmpty()) {
            int index = random.nextInt(availableBoats.size());
            return availableBoats.get(index);
        }
        return null;
    }

}
