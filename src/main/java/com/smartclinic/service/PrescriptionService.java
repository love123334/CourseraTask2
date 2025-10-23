package com.smartclinic.service;

import com.smartclinic.entity.Prescription;
import com.smartclinic.entity.Doctor;
import com.smartclinic.entity.Patient;
import com.smartclinic.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {
    
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    /**
     * Saves a new prescription
     * @param prescription Prescription entity to save
     * @return Saved prescription
     */
    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }
    
    /**
     * Gets all prescriptions for a doctor
     * @param doctor Doctor entity
     * @return List of prescriptions for the doctor
     */
    public List<Prescription> getPrescriptionsForDoctor(Doctor doctor) {
        return prescriptionRepository.findByDoctor(doctor);
    }
    
    /**
     * Gets all prescriptions for a patient
     * @param patient Patient entity
     * @return List of prescriptions for the patient
     */
    public List<Prescription> getPrescriptionsForPatient(Patient patient) {
        return prescriptionRepository.findByPatient(patient);
    }
    
    /**
     * Gets prescriptions for a specific doctor and patient
     * @param doctor Doctor entity
     * @param patient Patient entity
     * @return List of prescriptions for the doctor and patient
     */
    public List<Prescription> getPrescriptionsForDoctorAndPatient(Doctor doctor, Patient patient) {
        return prescriptionRepository.findByDoctorAndPatient(doctor, patient);
    }
    
    /**
     * Gets recent prescriptions for a patient
     * @param patient Patient entity
     * @param startDate Start date for filtering
     * @return List of recent prescriptions for the patient
     */
    public List<Prescription> getRecentPrescriptionsForPatient(Patient patient, LocalDateTime startDate) {
        return prescriptionRepository.findByPatientAndPrescriptionDateAfter(patient, startDate);
    }
    
    /**
     * Gets a prescription by ID
     * @param prescriptionId Prescription ID
     * @return Optional Prescription
     */
    public Optional<Prescription> getPrescriptionById(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId);
    }
    
    /**
     * Updates a prescription
     * @param prescription Prescription to update
     * @return Updated prescription
     */
    public Prescription updatePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }
    
    /**
     * Deletes a prescription
     * @param prescriptionId Prescription ID to delete
     */
    public void deletePrescription(Long prescriptionId) {
        prescriptionRepository.deleteById(prescriptionId);
    }
}
