package com.smartclinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
public class Prescription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Medication name is required")
    @Column(name = "medication_name", nullable = false)
    private String medicationName;
    
    @NotBlank(message = "Dosage is required")
    @Column(name = "dosage", nullable = false)
    private String dosage;
    
    @NotBlank(message = "Instructions are required")
    @Column(name = "instructions", columnDefinition = "TEXT", nullable = false)
    private String instructions;
    
    @Column(name = "prescription_date", nullable = false)
    private LocalDateTime prescriptionDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor is required")
    private Doctor doctor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "Patient is required")
    private Patient patient;
    
    // Constructors
    public Prescription() {}
    
    public Prescription(String medicationName, String dosage, String instructions, 
                       Doctor doctor, Patient patient) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.instructions = instructions;
        this.doctor = doctor;
        this.patient = patient;
        this.prescriptionDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMedicationName() {
        return medicationName;
    }
    
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
    
    public String getDosage() {
        return dosage;
    }
    
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    
    public String getInstructions() {
        return instructions;
    }
    
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    public LocalDateTime getPrescriptionDate() {
        return prescriptionDate;
    }
    
    public void setPrescriptionDate(LocalDateTime prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
