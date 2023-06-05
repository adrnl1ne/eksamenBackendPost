package com.example.eksamenbackend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class SailboatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean assigned;
    private SailboatType type;

    public enum SailboatType {
        FOOT_40("40foot"),
        FOOT_25("25foot"),
        FOOT_25_40("25-40foot");

        private String type;

        SailboatType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

    }
}
