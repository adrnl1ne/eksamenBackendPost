package com.example.eksamenbackend.controller;

import com.example.eksamenbackend.model.ParticipantModel;
import com.example.eksamenbackend.model.RaceModel;
import com.example.eksamenbackend.service.RaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class RaceController {

    private final RaceService raceService;

    RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping("/api/get/allParticipants/{id}/race")
    public ResponseEntity<List<ParticipantModel>> allParticipantsFromRace(@PathVariable Integer id) {

        Optional<RaceModel> checkRaceModel = raceService.findById(id);

        if (checkRaceModel.isPresent()) {
            List<ParticipantModel> raceModels;
            raceModels = raceService.findParticipantsByRaceId(id);
            System.out.println(raceModels.size());
            return ResponseEntity.ok(raceModels);
        } else {
            return ResponseEntity.ok(null);
        }

    }



}
