package com.example.eksamenbackend.Races.repository;


import com.example.eksamenbackend.Races.model.RaceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<RaceModel, Integer> {
}
