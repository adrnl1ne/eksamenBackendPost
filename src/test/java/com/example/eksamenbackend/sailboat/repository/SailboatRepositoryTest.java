package com.example.eksamenbackend.sailboat.repository;

import com.example.eksamenbackend.Races.model.ParticipantModel;
import com.example.eksamenbackend.Races.model.RaceModel;
import com.example.eksamenbackend.Races.repository.ParticipantRepository;
import com.example.eksamenbackend.Races.repository.RaceRepository;
import com.example.eksamenbackend.sailboat.model.SailboatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SailboatRepositoryTest {

    @Autowired
    SailboatRepository sailboatRepository;

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @Test
    void findAllSailboats(){
        SailboatModel sailboatModel = new SailboatModel();
        sailboatModel.setName("Test");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_40);
        sailboatRepository.save(sailboatModel);
        SailboatModel sailboatModel1 = new SailboatModel();
        sailboatModel1.setName("Test2");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_25);
        sailboatRepository.save(sailboatModel1);
        SailboatModel sailboatModel2 = new SailboatModel();
        sailboatModel2.setName("Test3");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_25_40);
        sailboatRepository.save(sailboatModel2);

        List<SailboatModel> sailboatModels = sailboatRepository.findAll();

        assertEquals(3, sailboatModels.size());
    }

    @Test
    void createSailboat() {
        SailboatModel sailboatModel = new SailboatModel();
        sailboatModel.setName("Test");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_40);
        sailboatRepository.save(sailboatModel);

        SailboatModel test = sailboatRepository.findById(sailboatModel.getId()).get();
        assertEquals("Test", test.getName());
    }

    @Test
    void updateSailboat() {
        SailboatModel sailboatModel = new SailboatModel();
        sailboatModel.setName("Test");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_40);
        sailboatRepository.save(sailboatModel);

        sailboatModel.setName("Test2");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_25);
        sailboatRepository.save(sailboatModel);

        SailboatModel test = sailboatRepository.findById(sailboatModel.getId()).get();
        assertEquals("Test2", test.getName());
    }


    @Test
    void deleteSailboat() {
        SailboatModel sailboatModel = new SailboatModel();
        sailboatModel.setName("Test");
        sailboatModel.setType(SailboatModel.SailboatType.FOOT_40);
        sailboatRepository.save(sailboatModel);

        sailboatRepository.deleteById(sailboatModel.getId());
        List<SailboatModel> sailboatModels = sailboatRepository.findAll();

        assertEquals(0, sailboatModels.size());
    }


    @Test
    void createRandomBoats() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            SailboatModel sailboatModel = new SailboatModel();
            sailboatModel.setName("Boat " + (i + 1));
            sailboatModel.setType(SailboatModel.SailboatType.values()[random.nextInt(SailboatModel.SailboatType.values().length)]);
            sailboatRepository.save(sailboatModel);
        }
    }



}