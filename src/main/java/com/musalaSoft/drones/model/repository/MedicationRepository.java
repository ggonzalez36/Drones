package com.musalaSoft.drones.model.repository;

import com.musalaSoft.drones.model.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository  extends JpaRepository<Medication, Long> {


}
