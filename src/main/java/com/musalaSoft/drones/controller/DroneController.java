package com.musalaSoft.drones.controller;


import com.musalaSoft.drones.model.entities.Drone;
import com.musalaSoft.drones.model.entities.Medication;
import com.musalaSoft.drones.model.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/drones")
public class DroneController {

    @Autowired
    private DroneRepository droneRepository;

    @GetMapping ("/available")
    public ResponseEntity<List<Drone>> getAvailableDronesForLoading() {
        // Get all drones that are in the IDLE state and have a battery level above 25%
        List<Drone> availableDrones = droneRepository.findByStateAndBatteryCapacityGreaterThan("IDLE", 25);

        // Return the list of available drones in the response body
        return ResponseEntity.ok(availableDrones);
    }
    @GetMapping("/{id}")
    public Drone getDroneById(@PathVariable Long id) {
        return droneRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Drone addDrone(@RequestBody Drone drone) {
        return droneRepository.save(drone);
    }

    @GetMapping("/{id}/medications")
    public List<Medication> getMedicationsForDrone(@PathVariable Long id) {
        Drone drone = droneRepository.findById(id).orElse(null);
        return drone != null ? drone.getMedications() : null;
    }

    @GetMapping("/{id}/battery")
    public int getDroneBatteryLevel(@PathVariable Long id) {
        Drone drone = droneRepository.findById(id).orElse(null);
        return drone != null ? drone.getBatteryCapacity() : 0;
    }

    @PutMapping("/{id}/load")
    public ResponseEntity<String> loadDrone(@PathVariable Long id, @RequestBody List<Medication> medications) {
        Drone drone = droneRepository.findById(id).orElse(null);
        if (drone != null) {
            double totalWeight = medications.stream().mapToDouble(Medication::getWeight).sum();
            if (totalWeight <= drone.getWeightLimit()) {
                if (drone.getBatteryCapacity() >= 25) {
                    drone.setMedications(medications);
                    drone.setState("LOADED");
                    droneRepository.save(drone);
                    return ResponseEntity.ok("Drone loaded successfully.");
                } else {
                    return ResponseEntity.badRequest().body("Drone battery level is too low to load.");
                }
            } else {
                return ResponseEntity.badRequest().body("Drone weight limit exceeded.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}