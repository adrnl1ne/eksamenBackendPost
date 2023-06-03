package com.example.eksamenbackend.Races.controller;

import com.example.eksamenbackend.Races.model.ParticipantModel;
import com.example.eksamenbackend.Races.service.ParticipantService;
import com.example.eksamenbackend.sailboat.model.SailboatModel;
import com.example.eksamenbackend.sailboat.service.SailboatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class ParticipantController {

    private final ParticipantService participantService;
    private final SailboatService sailboatService;

    ParticipantController(ParticipantService participantService, SailboatService sailboatService) {
        this.participantService = participantService;
        this.sailboatService = sailboatService;
    }

    @GetMapping("/api/get/findall/participant")
    public ResponseEntity<List<ParticipantModel>> findAllParticipants() {

        List<ParticipantModel> set = new ArrayList<>(participantService.findAll());

        System.out.println("found all participants");

        return ResponseEntity.ok(set);
    }


    @GetMapping("/api/get/allBoats/{id}/participant")
    public ResponseEntity<List<SailboatModel>> allBoatsFromParticipant(@PathVariable Integer id) {
        Optional<SailboatModel> sailboatModelOptional = sailboatService.findById(id);

        if (sailboatModelOptional.isPresent()) {
            SailboatModel sailboatModel = sailboatModelOptional.get();
            List<SailboatModel> participantModels = participantService.findBoatsByParticipantId(sailboatModel.getId());

            if (!participantModels.isEmpty()) {
                System.out.println("Participants found for sailboat with id: " + sailboatModel.getId());
                return ResponseEntity.ok(participantModels);
            }
        }

        System.out.println("No participants found for sailboat with id: " + id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/api/get/findByBoatId/{id}/participant")
    public ResponseEntity<ParticipantModel> findByBoatId(@PathVariable Integer id) {
        Optional<ParticipantModel> participantModelOptional = participantService.findByBoatId(id);

        if (participantModelOptional.isPresent()) {
            ParticipantModel participantModel = participantModelOptional.get();
            System.out.println("Participant found with id: " + participantModel.getId());
            return ResponseEntity.ok(participantModel);
        }

        System.out.println("No participant found with id: " + id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/api/post/create/participant")
    public ResponseEntity<String> createParticipant(@RequestBody ParticipantModel participantModel) {
        participantService.save(participantModel);

        String msg = "Created new participant with id " + participantModel.getId();

        if (participantService.save(participantModel) != null) {
            return ResponseEntity.ok(msg);
        } else {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PutMapping("/api/post/update/{id}/participant")
    public ResponseEntity<Map<String, String>> updateParticipant(@RequestBody ParticipantModel participantModel,
                                                                 @PathVariable Integer id) {

        String msg = "Updated participant with id " + participantModel.getId() + " and name " +
                participantModel.getBoatName() + " and type " + participantModel.getBoatType();

        Optional<ParticipantModel> oldParticipant = participantService.findById(id);

        if (oldParticipant.isPresent()) {
            ParticipantModel existingParticipant = oldParticipant.get();

            if (existingParticipant.getId() == participantModel.getId()) {
                // Retrieve the associated sailboat
                Optional<SailboatModel> sailboatOptional = sailboatService.findById(existingParticipant.getBoatId());

                if (sailboatOptional.isPresent()) {
                    SailboatModel sailboat = sailboatOptional.get();

                    // Update the sailboat data with participant data
                    sailboat.setType(participantModel.getBoatType());
                    sailboat.setName(participantModel.getBoatName());

                    // Save the updated sailboat
                    sailboatService.save(sailboat);
                }

                // Update the participant data
                existingParticipant.setBoatName(participantModel.getBoatName());
                existingParticipant.setBoatType(participantModel.getBoatType());
                participantService.save(existingParticipant);

                Map<String, String> map = new HashMap<>();
                map.put("message", msg);
                return ResponseEntity.ok(map);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("message", "IDs do not match");
                return ResponseEntity.badRequest().body(map);
            }
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("message", "Participant not found");
            return ResponseEntity.badRequest().body(map);
        }
    }



    @DeleteMapping("/api/post/delete/{id}/participant")
    public ResponseEntity<Map<String, String>> deleteParticipant(@PathVariable("id") int id) {
        Optional<ParticipantModel> participantOptional = participantService.findById(id);

        if (participantOptional.isPresent()) {
            ParticipantModel participant = participantOptional.get();

            // Set the boat reference to null, so it won't be deleted
            participant.setBoat(null);

            // Delete the participant
            participantService.deleteById(id);

            Map<String, String> map = new HashMap<>();
            map.put("message", "Participant deleted successfully");
            return ResponseEntity.ok(map);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("message", "Participant not found");
            return ResponseEntity.badRequest().body(map);
        }
    }



}
