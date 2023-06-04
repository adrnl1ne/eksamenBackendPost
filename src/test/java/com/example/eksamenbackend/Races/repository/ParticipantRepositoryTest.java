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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ParticipantRepositoryTest {

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    SailboatRepository sailboatRepository;

    @Autowired
    RaceRepository raceRepository;


    //Because we create 10 participants in a simulation method,
    // we expect to find 10 participants in the database
    @Test
    void findAllParticipants() {
        /*SailboatModel sailboatModel = new SailboatModel();
        sailboatModel.setName("Test");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_40);
        sailboatRepository.save(sailboatModel);
        RaceModel raceModel = new RaceModel();
        raceModel.setRaceType(SailboatModel.SailboatType.FOOT_40);
        raceRepository.save(raceModel);
        ParticipantModel participantModel = new ParticipantModel();
        participantModel.setBoatName(sailboatModel.getName());
        participantModel.setBoatId(sailboatModel.getId());
        participantModel.setRace(raceModel);
        participantRepository.save(participantModel);

         */
        List<ParticipantModel> participantModels = participantRepository.findAll();
        //because we create 10 participants in a simulation method, we expect to find 10 participants in the database
        assertEquals(10, participantModels.size());

    }

    @Test
    void createParticipant() {
        SailboatModel sailboatModel = new SailboatModel();
        sailboatModel.setName("Test");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_40);
        sailboatRepository.save(sailboatModel);

        RaceModel raceModel = new RaceModel();
        raceModel.setRaceType(SailboatModel.SailboatType.FOOT_40);
        raceRepository.save(raceModel);

        ParticipantModel participantModel = new ParticipantModel();
        participantModel.setBoatName(sailboatModel.getName());
        participantModel.setBoatId(sailboatModel.getId());
        participantModel.setRace(raceModel);
        participantRepository.save(participantModel);
        System.out.println(participantModel);

        ParticipantModel test = participantRepository.findById(participantModel.getBoatId()).get();
        System.out.println(participantModel.getBoatId());
        System.out.println(test.getBoatName());

        assertEquals("Test", test.getBoatName());
    }

    @Test
    void updateParticipant() {
        SailboatModel sailboatModel = new SailboatModel();
        sailboatModel.setName("Test");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_40);
        sailboatRepository.save(sailboatModel);

        RaceModel raceModel = new RaceModel();
        raceModel.setRaceType(SailboatModel.SailboatType.FOOT_40);
        raceRepository.save(raceModel);

        ParticipantModel participantModel = new ParticipantModel();
        participantModel.setBoatName(sailboatModel.getName());
        participantModel.setBoatId(sailboatModel.getId());
        participantModel.setRace(raceModel);
        participantRepository.save(participantModel);
        System.out.println(participantModel);

        sailboatModel.setName("Test2");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_25);
        sailboatRepository.save(sailboatModel);

        participantModel.setBoatName(sailboatModel.getName());
        participantModel.setBoatId(sailboatModel.getId());
        participantModel.setRace(raceModel);
        participantRepository.save(participantModel);
        System.out.println(participantModel);

        ParticipantModel test = participantRepository.findById(participantModel.getBoatId()).get();
        System.out.println(participantModel.getBoatId());
        System.out.println(test.getBoatName());

        assertEquals("Test2", test.getBoatName());
    }

    @Test
    void deleteParticipant() {
        SailboatModel sailboatModel = new SailboatModel();
        sailboatModel.setName("Test");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_40);
        sailboatRepository.save(sailboatModel);

        RaceModel raceModel = new RaceModel();
        raceModel.setRaceType(SailboatModel.SailboatType.FOOT_40);
        raceRepository.save(raceModel);

        ParticipantModel participantModel = new ParticipantModel();
        participantModel.setBoatName(sailboatModel.getName());
        participantModel.setBoatId(sailboatModel.getId());
        participantModel.setRace(raceModel);
        participantRepository.save(participantModel);
        System.out.println(participantModel);

        participantRepository.deleteById(participantModel.getId());
        List<ParticipantModel> participantModels = participantRepository.findAll();
        //Becasue we create 10 random participants, we expect to find 10 participants in the database
        //after creating one and deleting one
        assertEquals(10, participantModels.size());
    }

    @Test
    void allBoatsFromParticipant() {
        SailboatModel sailboatModel = new SailboatModel();
        sailboatModel.setName("Test");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_40);
        sailboatRepository.save(sailboatModel);

        List<SailboatModel> models = sailboatRepository.findBoatModelsById(sailboatModel.getId());

        assertEquals(1, models.size());
        assertEquals(sailboatModel.getName(), models.get(0).getName());
        assertEquals(sailboatModel.getType(), models.get(0).getType());
    }


}