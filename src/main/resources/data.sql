CREATE TABLE IF NOT EXISTS drone (
   drone_id INT AUTO_INCREMENT PRIMARY KEY,
     serial_number VARCHAR(100) NOT NULL,
       model VARCHAR(50) NOT NULL,
       weight_limit INT NOT NULL,
       battery_capacity INT NOT NULL,
       state VARCHAR(20) NOT NULL
);

INSERT INTO drone (drone_id,serial_number, model, weight_limit, battery_capacity, state) VALUES
(1,'DRN001', 'Lightweight', 500, 100, 'IDLE'),
(2,'DRN002', 'Middleweight', 500, 75, 'IDLE'),
(3,'DRN003', 'Cruiserweight', 500, 50, 'IDLE'),
(4,'DRN004', 'Heavyweight', 500, 25, 'IDLE'),
(5,'DRN005', 'Lightweight', 500, 100, 'IDLE'),
(6,'DRN006', 'Middleweight', 500, 75, 'IDLE'),
(7,'DRN007', 'Cruiserweight', 500, 50, 'IDLE');