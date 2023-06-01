package com.example.eksamenbackend.Races.service;


import com.example.eksamenbackend.Races.model.ParticipantModel;
import com.example.eksamenbackend.Races.repository.ParticipantRepository;
import com.example.eksamenbackend.sailboat.model.SailboatModel;
import com.example.eksamenbackend.sailboat.repository.SailboatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ParticipantService {

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    SailboatRepository sailboatRepository;

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
}
