package com.example.eksamenbackend.repository;

import com.example.eksamenbackend.model.ParticipantModel;
import com.example.eksamenbackend.model.SailboatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantModel, Integer> {

    List<ParticipantModel> findParticipantModelsByRaceId(Integer race);


    Optional<ParticipantModel> findByBoatId(Integer id);

    List<ParticipantModel> findParticipantModelsByBoatType(SailboatModel.SailboatType boatType);

}
