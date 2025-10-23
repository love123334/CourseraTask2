package com.smartclinic.repository;

import com.smartclinic.entity.Prescription;
import com.smartclinic.entity.Doctor;
import com.smartclinic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    
    List<Prescription> findByDoctor(Doctor doctor);
    
    List<Prescription> findByPatient(Patient patient);
    
    List<Prescription> findByDoctorAndPatient(Doctor doctor, Patient patient);
    
    @Query("SELECT p FROM Prescription p WHERE p.patient = :patient AND p.prescriptionDate >= :startDate")
    List<Prescription> findByPatientAndPrescriptionDateAfter(@Param("patient") Patient patient, @Param("startDate") LocalDateTime startDate);
}
