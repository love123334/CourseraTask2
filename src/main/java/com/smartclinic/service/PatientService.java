package com.smartclinic.service;

import com.smartclinic.entity.Patient;
import com.smartclinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    /**
     * Validates patient login credentials
     * @param email Patient's email
     * @param password Patient's password
     * @return Patient object if credentials are valid, null otherwise
     */
    public Patient validatePatientLogin(String email, String password) {
        Optional<Patient> patientOpt = patientRepository.findByEmail(email);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            if (patient.getPassword().equals(password)) {
                return patient;
            }
        }
        return null;
    }
    
    /**
     * Saves a new patient
     * @param patient Patient entity to save
     * @return Saved patient
     */
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }
    
    /**
     * Finds patient by email
     * @param email Patient's email
     * @return Optional Patient
     */
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
    
    /**
     * Finds patient by email or phone number
     * @param emailOrPhone Email or phone number
     * @return Optional Patient
     */
    public Optional<Patient> findByEmailOrPhoneNumber(String emailOrPhone) {
        return patientRepository.findByEmailOrPhoneNumber(emailOrPhone);
    }
    
    /**
     * Gets all patients
     * @return List of all patients
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    /**
     * Checks if patient exists by email
     * @param email Patient's email
     * @return true if exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return patientRepository.existsByEmail(email);
    }
    
    /**
     * Checks if patient exists by phone number
     * @param phoneNumber Patient's phone number
     * @return true if exists, false otherwise
     */
    public boolean existsByPhoneNumber(String phoneNumber) {
        return patientRepository.existsByPhoneNumber(phoneNumber);
    }
    
    /**
     * Finds patient by ID
     * @param id Patient ID
     * @return Optional Patient
     */
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }
}
