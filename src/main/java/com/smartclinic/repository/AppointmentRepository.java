package com.smartclinic.repository;

import com.smartclinic.entity.Appointment;
import com.smartclinic.entity.Doctor;
import com.smartclinic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByDoctor(Doctor doctor);
    
    List<Appointment> findByPatient(Patient patient);
    
    List<Appointment> findByDoctorAndAppointmentTimeBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT a FROM Appointment a WHERE a.doctor = :doctor AND DATE(a.appointmentTime) = :date")
    List<Appointment> findByDoctorAndDate(@Param("doctor") Doctor doctor, @Param("date") LocalDate date);
    
    @Query("SELECT a FROM Appointment a WHERE a.patient = :patient AND a.appointmentTime >= :startDate")
    List<Appointment> findByPatientAndAppointmentTimeAfter(@Param("patient") Patient patient, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT a FROM Appointment a WHERE a.doctor = :doctor AND a.appointmentTime = :appointmentTime")
    List<Appointment> findByDoctorAndAppointmentTime(@Param("doctor") Doctor doctor, @Param("appointmentTime") LocalDateTime appointmentTime);
}
