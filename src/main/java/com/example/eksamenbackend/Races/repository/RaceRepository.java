package com.example.eksamenbackend.Races.repository;


import com.example.eksamenbackend.Races.model.RaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<RaceModel, Integer> {

}
