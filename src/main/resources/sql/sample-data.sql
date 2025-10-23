-- Smart Clinic Management System - Sample Data
USE smart_clinic_db;

-- Insert sample doctors
INSERT INTO doctors (first_name, last_name, email, password, specialty, phone_number, available_start_time, available_end_time) VALUES
('John', 'Smith', 'dr.smith@smartclinic.com', 'password123', 'Cardiology', '555-0101', '09:00:00', '17:00:00'),
('Sarah', 'Johnson', 'dr.johnson@smartclinic.com', 'password123', 'Dermatology', '555-0102', '08:00:00', '16:00:00'),
('Michael', 'Brown', 'dr.brown@smartclinic.com', 'password123', 'Neurology', '555-0103', '10:00:00', '18:00:00'),
('Emily', 'Davis', 'dr.davis@smartclinic.com', 'password123', 'Pediatrics', '555-0104', '09:30:00', '17:30:00'),
('David', 'Wilson', 'dr.wilson@smartclinic.com', 'password123', 'Orthopedics', '555-0105', '08:30:00', '16:30:00');

-- Insert sample patients
INSERT INTO patients (first_name, last_name, email, password, phone_number, date_of_birth, address) VALUES
('Alice', 'Anderson', 'alice.anderson@email.com', 'password123', '555-0201', '1985-03-15', '123 Main St, City, State 12345'),
('Bob', 'Brown', 'bob.brown@email.com', 'password123', '555-0202', '1990-07-22', '456 Oak Ave, City, State 12345'),
('Carol', 'Clark', 'carol.clark@email.com', 'password123', '555-0203', '1978-11-08', '789 Pine Rd, City, State 12345'),
('David', 'Davis', 'david.davis@email.com', 'password123', '555-0204', '1992-05-12', '321 Elm St, City, State 12345'),
('Eve', 'Evans', 'eve.evans@email.com', 'password123', '555-0205', '1988-09-30', '654 Maple Dr, City, State 12345');

-- Insert sample appointments
INSERT INTO appointments (appointment_time, status, notes, doctor_id, patient_id) VALUES
('2024-01-15 10:00:00', 'SCHEDULED', 'Regular checkup', 1, 1),
('2024-01-15 11:00:00', 'CONFIRMED', 'Follow-up visit', 1, 2),
('2024-01-16 09:00:00', 'SCHEDULED', 'Skin examination', 2, 3),
('2024-01-16 14:00:00', 'COMPLETED', 'Neurological assessment', 3, 4),
('2024-01-17 10:30:00', 'SCHEDULED', 'Pediatric consultation', 4, 5),
('2024-01-17 15:00:00', 'CONFIRMED', 'Orthopedic evaluation', 5, 1),
('2024-01-18 09:30:00', 'SCHEDULED', 'Cardiology follow-up', 1, 3),
('2024-01-18 11:00:00', 'SCHEDULED', 'Dermatology consultation', 2, 4);

-- Insert sample prescriptions
INSERT INTO prescriptions (medication_name, dosage, instructions, prescription_date, doctor_id, patient_id) VALUES
('Lisinopril', '10mg', 'Take once daily with food', '2024-01-15 10:30:00', 1, 1),
('Metformin', '500mg', 'Take twice daily with meals', '2024-01-15 11:15:00', 1, 2),
('Hydrocortisone Cream', '1%', 'Apply to affected area twice daily', '2024-01-16 09:15:00', 2, 3),
('Gabapentin', '300mg', 'Take three times daily', '2024-01-16 14:15:00', 3, 4),
('Amoxicillin', '250mg', 'Take every 8 hours for 7 days', '2024-01-17 10:45:00', 4, 5),
('Ibuprofen', '400mg', 'Take as needed for pain, max 3 times daily', '2024-01-17 15:15:00', 5, 1);

-- Insert doctor available times
INSERT INTO doctor_available_times (doctor_id, available_time) VALUES
(1, '09:00:00'), (1, '10:00:00'), (1, '11:00:00'), (1, '12:00:00'), (1, '13:00:00'), (1, '14:00:00'), (1, '15:00:00'), (1, '16:00:00'),
(2, '08:00:00'), (2, '09:00:00'), (2, '10:00:00'), (2, '11:00:00'), (2, '12:00:00'), (2, '13:00:00'), (2, '14:00:00'), (2, '15:00:00'),
(3, '10:00:00'), (3, '11:00:00'), (3, '12:00:00'), (3, '13:00:00'), (3, '14:00:00'), (3, '15:00:00'), (3, '16:00:00'), (3, '17:00:00'),
(4, '09:30:00'), (4, '10:30:00'), (4, '11:30:00'), (4, '12:30:00'), (4, '13:30:00'), (4, '14:30:00'), (4, '15:30:00'), (4, '16:30:00'),
(5, '08:30:00'), (5, '09:30:00'), (5, '10:30:00'), (5, '11:30:00'), (5, '12:30:00'), (5, '13:30:00'), (5, '14:30:00'), (5, '15:30:00');
