package com.smartclinic.controller;

import com.smartclinic.entity.Doctor;
import com.smartclinic.entity.Patient;
import com.smartclinic.entity.Prescription;
import com.smartclinic.service.DoctorService;
import com.smartclinic.service.PatientService;
import com.smartclinic.service.PrescriptionService;
import com.smartclinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {
    
    @Autowired
    private PrescriptionService prescriptionService;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private TokenService tokenService;
    
    /**
     * POST endpoint saves a prescription with token and request body validation
     * @param prescriptionRequest Prescription data
     * @param token JWT token for authentication
     * @return ResponseEntity with success or error message
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> savePrescription(
            @RequestBody Map<String, Object> prescriptionRequest,
            @RequestHeader("Authorization") String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null) {
            response.put("success", false);
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            // Get doctor from token
            Optional<Doctor> doctorOpt = doctorService.findByEmail(email);
            if (doctorOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Doctor not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Doctor doctor = doctorOpt.get();
            
            // Get patient
            Long patientId = Long.valueOf(prescriptionRequest.get("patientId").toString());
            Optional<Patient> patientOpt = patientService.findById(patientId);
            if (patientOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Patient not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Patient patient = patientOpt.get();
            
            // Create prescription
            Prescription prescription = new Prescription();
            prescription.setMedicationName(prescriptionRequest.get("medicationName").toString());
            prescription.setDosage(prescriptionRequest.get("dosage").toString());
            prescription.setInstructions(prescriptionRequest.get("instructions").toString());
            prescription.setDoctor(doctor);
            prescription.setPatient(patient);
            
            Prescription savedPrescription = prescriptionService.savePrescription(prescription);
            
            response.put("success", true);
            response.put("prescription", savedPrescription);
            response.put("message", "Prescription saved successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error saving prescription: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Gets prescriptions for a patient
     * @param patientId Patient ID
     * @param token JWT token for authentication
     * @return ResponseEntity with prescriptions
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getPrescriptionsForPatient(
            @PathVariable Long patientId,
            @RequestHeader("Authorization") String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null) {
            response.put("success", false);
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            Optional<Patient> patientOpt = patientService.findById(patientId);
            if (patientOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Patient not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Patient patient = patientOpt.get();
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsForPatient(patient);
            
            response.put("success", true);
            response.put("prescriptions", prescriptions);
            response.put("message", "Prescriptions retrieved successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving prescriptions: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Gets prescriptions for a doctor
     * @param token JWT token for authentication
     * @return ResponseEntity with prescriptions
     */
    @GetMapping("/doctor")
    public ResponseEntity<Map<String, Object>> getPrescriptionsForDoctor(
            @RequestHeader("Authorization") String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null) {
            response.put("success", false);
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            Optional<Doctor> doctorOpt = doctorService.findByEmail(email);
            if (doctorOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Doctor not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Doctor doctor = doctorOpt.get();
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsForDoctor(doctor);
            
            response.put("success", true);
            response.put("prescriptions", prescriptions);
            response.put("message", "Prescriptions retrieved successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving prescriptions: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
