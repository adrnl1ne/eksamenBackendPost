package com.example.eksamenbackend.service;

import com.example.eksamenbackend.repository.ParticipantRepository;
import com.example.eksamenbackend.repository.RaceRepository;
import com.example.eksamenbackend.model.SailboatModel;
import com.example.eksamenbackend.repository.SailboatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SailboatService {

    @Autowired
    SailboatRepository sailboatRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    RaceService raceService;

    @Autowired
    ParticipantService participantService;


    public SailboatService(SailboatRepository sailboatRepository){
        this.sailboatRepository = sailboatRepository;
    }

    public Set<SailboatModel> findAll() {
        return new HashSet<>(sailboatRepository.findAll());
    }


    public SailboatModel save(SailboatModel object) {
        return sailboatRepository.save(object);
    }

    public void deleteById(Integer integer) {
        sailboatRepository.deleteById(integer);
    }


    public Optional<SailboatModel> findById(Integer integer) {
        return sailboatRepository.findById(integer);
    }




    public void initializeBoats() {
        if (sailboatRepository.count() == 0) {
            generateRandomBoats();
        }

    }

    private void generateRandomBoats() {
        Random random = new Random();
        List<SailboatModel.SailboatType> boatTypes = Arrays.asList(
                SailboatModel.SailboatType.FOOT_40,
                SailboatModel.SailboatType.FOOT_25,
                SailboatModel.SailboatType.FOOT_25_40
        );

        for (int i = 0; i < 10; i++) {
            SailboatModel sailboat = new SailboatModel();
            sailboat.setName(generateRandomName());
            sailboat.setType(getRandomBoatType(boatTypes, random));
            sailboatRepository.save(sailboat);
        }
    }

    private String generateRandomName() {
        Random random = new Random();
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char randomChar = (char) (random.nextInt(26) + 'A');
            name.append(randomChar);
        }
        return name.toString();
    }

    private SailboatModel.SailboatType getRandomBoatType(List<SailboatModel.SailboatType> boatTypes, Random random) {
        int index = random.nextInt(boatTypes.size());
        return boatTypes.get(index);
    }





}
