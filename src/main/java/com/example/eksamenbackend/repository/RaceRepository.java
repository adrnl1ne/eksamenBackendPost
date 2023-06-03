package com.example.eksamenbackend.repository;


import com.example.eksamenbackend.model.RaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<RaceModel, Integer> {

}
