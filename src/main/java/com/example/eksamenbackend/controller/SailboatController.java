package com.example.eksamenbackend.controller;


import com.example.eksamenbackend.model.ParticipantModel;
import com.example.eksamenbackend.service.ParticipantService;
import com.example.eksamenbackend.model.SailboatModel;
import com.example.eksamenbackend.service.SailboatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class SailboatController {

    private final SailboatService sailboatService;
    private final ParticipantService participantService;

    SailboatController(SailboatService sailboatService, ParticipantService participantService) {
        this.sailboatService = sailboatService;
        this.participantService = participantService;
    }


    // This method is used to find all sailboats in the database.
    @GetMapping("/api/get/findAll/sailboats")
    public ResponseEntity<Set<SailboatModel>> findAllSailboats() {
        Set<SailboatModel> models = new HashSet<>(sailboatService.findAll());

        System.out.println("found all sailboats");

        return ResponseEntity.ok(models);
    }

    // This method is used to find a sailboat by its id.
    @PostMapping("/api/post/create/sailboat")
    public ResponseEntity<String> createSailboat(@RequestBody SailboatModel sailboatModel) {
        sailboatService.save(sailboatModel);

        String msg = "Created new sailboat with id " + sailboatModel.getName() + " and type " + sailboatModel.getType();

        if (sailboatService.save(sailboatModel) != null) {
            return ResponseEntity.ok(msg);
        } else {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    // This method is used to update a sailboat by its id.
    @PostMapping("/api/post/update/{id}/sailboat")
    public ResponseEntity<Map<String, String>> updateSailboat(@RequestBody SailboatModel sailboatModel,
                                                              @PathVariable() Integer id) {

        String msg = "Updated sailboat with name " + sailboatModel.getName() + " and type " + sailboatModel.getType();

        Optional<SailboatModel> oldSailboat = sailboatService.findById(id);
        if (oldSailboat.isPresent() && id == sailboatModel.getId()) {
            SailboatModel updatedSailboat = sailboatService.save(sailboatModel);

            // Retrieve and update the participant associated with the updated sailboat
            Optional<ParticipantModel> participantOptional = participantService.findByBoatId(updatedSailboat.getId());
            if (participantOptional.isPresent()) {
                ParticipantModel participant = participantOptional.get();
                participant.setBoatType(updatedSailboat.getType());
                participant.setBoatName(updatedSailboat.getName());
                participantService.save(participant);
            }

            Map<String, String> map = new HashMap<>();
            map.put("message", msg);
            return ResponseEntity.ok(map);

        } else {
            Map<String, String> map = new HashMap<>();
            map.put("message", "Something went wrong");
            return ResponseEntity.badRequest().body(map);
        }
    }


    // This method is used to delete a sailboat by its id.
    @PostMapping("/api/post/delete/{id}/sailboat")
    public ResponseEntity<String> deleteSailboat(@PathVariable() Integer id) {

        Optional<SailboatModel> model = sailboatService.findById(id);

        if (model.isPresent()) {
            sailboatService.deleteById(id);
            return ResponseEntity.ok("Deleted sailboat with id " + id);
        } else {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

}
