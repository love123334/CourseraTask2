-- Smart Clinic Management System - Stored Procedures

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS smart_clinic_db;
USE smart_clinic_db;

-- Stored Procedure 1: Get Daily Appointment Report by Doctor
DELIMITER //
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(
    IN doctor_id_param BIGINT,
    IN report_date DATE
)
BEGIN
    SELECT 
        d.id as doctor_id,
        d.first_name,
        d.last_name,
        d.specialty,
        DATE(a.appointment_time) as appointment_date,
        COUNT(a.id) as total_appointments,
        COUNT(CASE WHEN a.status = 'SCHEDULED' THEN 1 END) as scheduled_appointments,
        COUNT(CASE WHEN a.status = 'CONFIRMED' THEN 1 END) as confirmed_appointments,
        COUNT(CASE WHEN a.status = 'CANCELLED' THEN 1 END) as cancelled_appointments,
        COUNT(CASE WHEN a.status = 'COMPLETED' THEN 1 END) as completed_appointments
    FROM doctors d
    LEFT JOIN appointments a ON d.id = a.doctor_id AND DATE(a.appointment_time) = report_date
    WHERE d.id = doctor_id_param
    GROUP BY d.id, d.first_name, d.last_name, d.specialty, DATE(a.appointment_time);
END //
DELIMITER ;

-- Stored Procedure 2: Get Doctor with Most Patients by Month
DELIMITER //
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(
    IN target_month INT,
    IN target_year INT
)
BEGIN
    SELECT 
        d.id as doctor_id,
        d.first_name,
        d.last_name,
        d.specialty,
        COUNT(DISTINCT a.patient_id) as patient_count,
        MONTH(a.appointment_time) as month,
        YEAR(a.appointment_time) as year
    FROM doctors d
    INNER JOIN appointments a ON d.id = a.doctor_id
    WHERE MONTH(a.appointment_time) = target_month 
      AND YEAR(a.appointment_time) = target_year
    GROUP BY d.id, d.first_name, d.last_name, d.specialty, MONTH(a.appointment_time), YEAR(a.appointment_time)
    ORDER BY patient_count DESC
    LIMIT 1;
END //
DELIMITER ;

-- Stored Procedure 3: Get Doctor with Most Patients by Year
DELIMITER //
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(
    IN target_year INT
)
BEGIN
    SELECT 
        d.id as doctor_id,
        d.first_name,
        d.last_name,
        d.specialty,
        COUNT(DISTINCT a.patient_id) as patient_count,
        YEAR(a.appointment_time) as year
    FROM doctors d
    INNER JOIN appointments a ON d.id = a.doctor_id
    WHERE YEAR(a.appointment_time) = target_year
    GROUP BY d.id, d.first_name, d.last_name, d.specialty, YEAR(a.appointment_time)
    ORDER BY patient_count DESC
    LIMIT 1;
END //
DELIMITER ;

-- Stored Procedure 4: Get Monthly Appointment Statistics
DELIMITER //
CREATE PROCEDURE GetMonthlyAppointmentStatistics(
    IN target_month INT,
    IN target_year INT
)
BEGIN
    SELECT 
        DATE(a.appointment_time) as appointment_date,
        COUNT(a.id) as total_appointments,
        COUNT(CASE WHEN a.status = 'SCHEDULED' THEN 1 END) as scheduled_count,
        COUNT(CASE WHEN a.status = 'CONFIRMED' THEN 1 END) as confirmed_count,
        COUNT(CASE WHEN a.status = 'CANCELLED' THEN 1 END) as cancelled_count,
        COUNT(CASE WHEN a.status = 'COMPLETED' THEN 1 END) as completed_count
    FROM appointments a
    WHERE MONTH(a.appointment_time) = target_month 
      AND YEAR(a.appointment_time) = target_year
    GROUP BY DATE(a.appointment_time)
    ORDER BY appointment_date;
END //
DELIMITER ;

-- Stored Procedure 5: Get Patient Appointment History
DELIMITER //
CREATE PROCEDURE GetPatientAppointmentHistory(
    IN patient_id_param BIGINT,
    IN start_date DATE,
    IN end_date DATE
)
BEGIN
    SELECT 
        a.id as appointment_id,
        a.appointment_time,
        a.status,
        a.notes,
        d.first_name as doctor_first_name,
        d.last_name as doctor_last_name,
        d.specialty
    FROM appointments a
    INNER JOIN doctors d ON a.doctor_id = d.id
    WHERE a.patient_id = patient_id_param
      AND DATE(a.appointment_time) BETWEEN start_date AND end_date
    ORDER BY a.appointment_time DESC;
END //
DELIMITER ;

-- Stored Procedure 6: Get Doctor Performance Report
DELIMITER //
CREATE PROCEDURE GetDoctorPerformanceReport(
    IN doctor_id_param BIGINT,
    IN start_date DATE,
    IN end_date DATE
)
BEGIN
    SELECT 
        d.id as doctor_id,
        d.first_name,
        d.last_name,
        d.specialty,
        COUNT(a.id) as total_appointments,
        COUNT(CASE WHEN a.status = 'COMPLETED' THEN 1 END) as completed_appointments,
        COUNT(CASE WHEN a.status = 'CANCELLED' THEN 1 END) as cancelled_appointments,
        COUNT(DISTINCT a.patient_id) as unique_patients,
        ROUND(COUNT(CASE WHEN a.status = 'COMPLETED' THEN 1 END) * 100.0 / COUNT(a.id), 2) as completion_rate
    FROM doctors d
    LEFT JOIN appointments a ON d.id = a.doctor_id 
        AND DATE(a.appointment_time) BETWEEN start_date AND end_date
    WHERE d.id = doctor_id_param
    GROUP BY d.id, d.first_name, d.last_name, d.specialty;
END //
DELIMITER ;

-- Stored Procedure 7: Get Prescription Statistics
DELIMITER //
CREATE PROCEDURE GetPrescriptionStatistics(
    IN start_date DATE,
    IN end_date DATE
)
BEGIN
    SELECT 
        d.specialty,
        COUNT(p.id) as total_prescriptions,
        COUNT(DISTINCT p.patient_id) as unique_patients,
        COUNT(DISTINCT p.doctor_id) as unique_doctors
    FROM prescriptions p
    INNER JOIN doctors d ON p.doctor_id = d.id
    WHERE DATE(p.prescription_date) BETWEEN start_date AND end_date
    GROUP BY d.specialty
    ORDER BY total_prescriptions DESC;
END //
DELIMITER ;

-- Stored Procedure 8: Get Available Time Slots for Doctor
DELIMITER //
CREATE PROCEDURE GetAvailableTimeSlots(
    IN doctor_id_param BIGINT,
    IN target_date DATE
)
BEGIN
    SELECT 
        d.id as doctor_id,
        d.first_name,
        d.last_name,
        d.available_start_time,
        d.available_end_time,
        GROUP_CONCAT(DISTINCT TIME(a.appointment_time) ORDER BY TIME(a.appointment_time)) as booked_times
    FROM doctors d
    LEFT JOIN appointments a ON d.id = a.doctor_id 
        AND DATE(a.appointment_time) = target_date
        AND a.status IN ('SCHEDULED', 'CONFIRMED')
    WHERE d.id = doctor_id_param
    GROUP BY d.id, d.first_name, d.last_name, d.available_start_time, d.available_end_time;
END //
DELIMITER ;
