package com.example.eksamenbackend.Races.repository;

import com.example.eksamenbackend.model.ParticipantModel;
import com.example.eksamenbackend.model.RaceModel;
import com.example.eksamenbackend.repository.ParticipantRepository;
import com.example.eksamenbackend.repository.RaceRepository;
import com.example.eksamenbackend.model.SailboatModel;
import com.example.eksamenbackend.repository.SailboatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RaceRepositoryTest {

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    SailboatRepository sailboatRepository;

    @Autowired
    ParticipantRepository participantRepository;


    @Test
    void allParticipantsFromRace() {
        RaceModel raceModel = new RaceModel();
        raceModel.setRaceType(SailboatModel.SailboatType.FOOT_40);
        raceRepository.save(raceModel);


        SailboatModel sailboatModel = new SailboatModel();
        sailboatModel.setName("Test");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_40);
        sailboatRepository.save(sailboatModel);

        ParticipantModel participantModel = new ParticipantModel();
        participantModel.setBoatId(sailboatModel.getId());
        participantModel.setRace(raceModel);
        participantRepository.save(participantModel);

        List<ParticipantModel> participantModels =
                participantRepository.findParticipantModelsByRaceId(raceModel.getId());

        assertEquals(1, participantModels.size());

    }


    @Test
    void findAllRaces() {
        RaceModel raceModel = new RaceModel();
        raceModel.setRaceType(SailboatModel.SailboatType.FOOT_40);
        raceRepository.save(raceModel);
        RaceModel raceModel1 = new RaceModel();
        raceModel1.setRaceType(SailboatModel.SailboatType.FOOT_25);
        raceRepository.save(raceModel1);
        RaceModel raceModel2 = new RaceModel();
        raceModel2.setRaceType(SailboatModel.SailboatType.FOOT_25_40);
        raceRepository.save(raceModel2);

        List<RaceModel> raceModels = raceRepository.findAll();

        assertEquals(135, raceModels.size());
    }

}