package com.musalaSoft.drones.model.entities;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



import java.util.List;

@Getter
@Setter
@Entity
@Table(name="drone")
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

}
