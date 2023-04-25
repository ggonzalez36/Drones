package com.musalaSoft.drones.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;





@Getter
@Setter
@Entity
@Table(name="medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double weight;

    private String code;

    private String image;


}