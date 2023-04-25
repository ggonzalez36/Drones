package com.musalaSoft.drones.model.repository;


import com.musalaSoft.drones.model.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {


    List<Drone> findByStateAndBatteryCapacityGreaterThan(String idle, int i);
}
