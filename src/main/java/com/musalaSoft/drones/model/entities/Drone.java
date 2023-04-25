package com.musalaSoft.drones.model.entities;



import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Getter
@Setter
@Entity
@Table(name="drone")
@NoArgsConstructor
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long droneId;

    private String serialNumber;

    private String model;

    private double weightLimit;

    private int batteryCapacity;

    private String state;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "medication",
            joinColumns = @JoinColumn(name = "droneId"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Medication> medications;


    public Drone(long droneId, String state, int batteryCapacity, int weightLimit) {
        this.droneId=droneId;
        this.state=state;
        this.batteryCapacity=batteryCapacity;
        this.weightLimit=weightLimit;
    }
}
