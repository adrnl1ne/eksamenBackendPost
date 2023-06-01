package com.example.eksamenbackend.Races.repository;

import com.example.eksamenbackend.Races.model.ParticipantModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<ParticipantModel, Integer> {

    List<ParticipantModel> findParticipantModelsByRaceId(Integer race);


    Optional<ParticipantModel> findByBoatId(Integer id);
}
