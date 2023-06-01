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

    @GetMapping("/api/get/findAll/allParticipants")
    public ResponseEntity<Set<ParticipantModel>> findAllParticipants() {
        Set<ParticipantModel> models = new HashSet<>(participantService.findAll());

        System.out.println("found all participants");

        return ResponseEntity.ok(models);
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

    @PostMapping("/api/post/update/{id}/participant")
    public ResponseEntity<Map<String, String>> updateParticipant(@RequestBody ParticipantModel participantModel,
                                                                 @PathVariable() Integer id) {

        String msg = "Updated participant with id " + participantModel.getId() + "and type " + participantModel.getBoatType();
        System.out.println("test");
        Optional<ParticipantModel> oldParticipant = participantService.findById(id);
        if (oldParticipant.isPresent()) {
            System.out.println("test1");
            if (id == participantModel.getId())
                System.out.println("test2");
            participantService.save(participantModel);
            System.out.println("test3");
            Map<String, String> map = new HashMap<>();
            map.put("message", msg);
            return ResponseEntity.ok(map);

        } else {
            Map<String, String> map = new HashMap<>();
            map.put("message", "Something went wrong");
            return ResponseEntity.badRequest().body(map);
        }


    }

    @PostMapping("/api/post/delete/{id}/participant")
    public ResponseEntity<String> deleteParticipant(@PathVariable() Integer id) {

        Optional<ParticipantModel> model = participantService.findById(id);

        if (model.isPresent()) {
            participantService.deleteById(id);
            return ResponseEntity.ok("Deleted participant with id " + id);
        } else {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

}
