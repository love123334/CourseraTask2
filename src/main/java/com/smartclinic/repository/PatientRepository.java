package com.smartclinic.repository;

import com.smartclinic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // Method to retrieve patient by email using derived query
    Optional<Patient> findByEmail(String email);
    
    // Method to retrieve patient using either email or phone number
    @Query("SELECT p FROM Patient p WHERE p.email = :emailOrPhone OR p.phoneNumber = :emailOrPhone")
    Optional<Patient> findByEmailOrPhoneNumber(@Param("emailOrPhone") String emailOrPhone);
    
    // Method to check if patient exists by email
    boolean existsByEmail(String email);
    
    // Method to check if patient exists by phone number
    boolean existsByPhoneNumber(String phoneNumber);
}
