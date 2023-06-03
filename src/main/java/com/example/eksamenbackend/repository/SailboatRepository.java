package com.example.eksamenbackend.repository;

import com.example.eksamenbackend.model.SailboatModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SailboatRepository extends JpaRepository<SailboatModel, Integer> {

    List<SailboatModel> findBoatModelsById(Integer sailboatId);


    List<SailboatModel> findByAssigned(boolean b);
}
