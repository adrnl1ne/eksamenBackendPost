package com.example.eksamenbackend.Races.service;


import com.example.eksamenbackend.Races.model.ParticipantModel;
import com.example.eksamenbackend.Races.model.RaceModel;
import com.example.eksamenbackend.Races.repository.ParticipantRepository;
import com.example.eksamenbackend.Races.repository.RaceRepository;
import com.example.eksamenbackend.sailboat.model.SailboatModel;
import com.example.eksamenbackend.sailboat.repository.SailboatRepository;
import jakarta.annotation.PostConstruct;
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

    public ParticipantService(ParticipantRepository participantRepository){
        this.participantRepository = participantRepository;
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


    public void initializeParticipants() {
        if (participantRepository.count() == 0) {
            generateRandomParticipants();
        }
    }

    private void generateRandomParticipants() {
        Random random = new Random();
        List<SailboatModel.SailboatType> boatTypes = Arrays.asList(
                SailboatModel.SailboatType.FOOT_40,
                SailboatModel.SailboatType.FOOT_25,
                SailboatModel.SailboatType.FOOT_25_40
        );

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



    private RaceModel findMatchingRace(SailboatModel.SailboatType sailboatType, List<RaceModel> races) {
        for (RaceModel race : races) {
            if (race.getRaceType() == sailboatType) {
                return race;
            }
        }
        return null;
    }


    private SailboatModel getRandomAvailableBoat(Random random) {
        List<SailboatModel> availableBoats = sailboatRepository.findByAssigned(false);
        if (!availableBoats.isEmpty()) {
            int index = random.nextInt(availableBoats.size());
            return availableBoats.get(index);
        }
        return null;
    }

}
