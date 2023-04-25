package com.musalaSoft.drones.Controller;


import com.musalaSoft.drones.controller.DroneController;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musalaSoft.drones.model.entities.Drone;
import com.musalaSoft.drones.model.entities.Medication;
import com.musalaSoft.drones.model.repository.DroneRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.musalaSoft.drones.model.entities.Drone;
import com.musalaSoft.drones.model.entities.Medication;
import com.musalaSoft.drones.model.repository.DroneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class DroneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private DroneController droneController;

    @MockBean
    private DroneRepository droneRepository;

    @Mock
    private DroneRepository droneRepo;

    private Drone testDrone;

    private Medication medication;

    @BeforeEach
    public void setup() {
        testDrone = new Drone();
        testDrone.setDroneId(1L);
        testDrone.setSerialNumber("Test Drone");
        testDrone.setBatteryCapacity(50);
        testDrone.setState("IDLE");
        testDrone.setWeightLimit(500);
        testDrone.setMedications(new ArrayList<>());

        medication = new Medication();
        medication.setId(1L);
        medication.setName("Ibuprofen");
        medication.setWeight(10.0);
    }

    @Test
    void getAvailableDronesForLoading_shouldReturnAvailableDrones() throws Exception {
        // Arrange
        Drone drone = new Drone(1L, "IDLE", 50, 1000);
        List<Drone> availableDrones = new ArrayList<>();
        availableDrones.add(drone);
        Mockito.when(droneRepository.findByStateAndBatteryCapacityGreaterThan("IDLE", 25)).thenReturn(availableDrones);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/drones/available"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].droneId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].state").value("IDLE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].batteryCapacity").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].weightLimit").value(1000));
    }

    @Test
    void getDroneById_shouldReturnDroneIfExists() throws Exception {
        // Arrange
        Drone drone = new Drone(1L, "IDLE", 50, 1000);
        Mockito.when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/drones/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.droneId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("IDLE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batteryCapacity").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weightLimit").value(1000));
    }


    @Test
    public void testAddDrone() {
        when(droneRepo.save(any(Drone.class))).thenReturn(testDrone);

        Drone drone = droneController.addDrone(testDrone);

        verify(droneRepo, times(1)).save(testDrone);
        assertEquals(testDrone, drone);
    }


    @Test
    public void testGetMedicationsForDrone() {
        testDrone.getMedications().add(medication);
        when(droneRepo.findById(anyLong())).thenReturn(Optional.of(testDrone));

        List<Medication> medications = droneController.getMedicationsForDrone(1L);

        verify(droneRepo, times(1)).findById(1L);
        assertEquals(testDrone.getMedications(), medications);
    }

    @Test
    public void testGetDroneBatteryLevel() {
        when(droneRepo.findById(anyLong())).thenReturn(Optional.of(testDrone));

        int batteryLevel = droneController.getDroneBatteryLevel(1L);

        verify(droneRepo, times(1)).findById(1L);
        assertEquals(testDrone.getBatteryCapacity(), batteryLevel);
    }

    @Test
    void testLoadDrone_Success() {
        when(droneRepo.findById(testDrone.getDroneId())).thenReturn(java.util.Optional.of(testDrone));
        when(droneRepo.save(any(Drone.class))).thenReturn(testDrone);

        // Call controller method
        ResponseEntity<String> response = droneController.loadDrone(testDrone.getDroneId(), Collections.singletonList(medication));

        // Assert response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Drone loaded successfully.", response.getBody());
        assertEquals("LOADED", testDrone.getState());
        assertEquals(Collections.singletonList(medication), testDrone.getMedications());
    }


}
