package com.example.eksamenbackend.Races.repository;

import com.example.eksamenbackend.Races.model.ParticipantModel;
import com.example.eksamenbackend.Races.model.RaceModel;
import com.example.eksamenbackend.sailboat.model.SailboatModel;
import com.example.eksamenbackend.sailboat.repository.SailboatRepository;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
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
    void addParticipantToRace() {
        // Creating a new participant
        ParticipantModel participant = new ParticipantModel();

        //Properties
        participant.setBoatId(1);

        // Retrieve by ID
        Optional<RaceModel> raceOptional = raceRepository.findById(1);


        if (raceOptional.isPresent()) {
            RaceModel raceModel = raceOptional.get();

            participant.setRace(raceModel);

            raceModel.getParticipantModels().add(participant);

            participantRepository.save(participant);
            raceRepository.save(raceModel);
        } else {
            return;
        }
    }




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

        raceModel.getParticipantModels().add(participantModel);
        raceRepository.save(raceModel);

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

        assertEquals(3, raceModels.size());
    }

}