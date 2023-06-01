package com.example.eksamenbackend.sailboat.repository;

import com.example.eksamenbackend.sailboat.model.SailboatModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SailboatRepository extends JpaRepository<SailboatModel, Integer> {

    List<SailboatModel> findBoatModelsById(Integer sailboatId);


}
