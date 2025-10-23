package com.smartclinic.controller;

import com.smartclinic.entity.Patient;
import com.smartclinic.service.PatientService;
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
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private TokenService tokenService;
    
    /**
     * Patient login endpoint
     * @param loginRequest Login credentials
     * @return ResponseEntity with login result
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> patientLogin(@RequestBody Map<String, String> loginRequest) {
        Map<String, Object> response = new HashMap<>();
        
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        
        Patient patient = patientService.validatePatientLogin(email, password);
        if (patient != null) {
            String token = tokenService.generateToken(patient.getEmail());
            response.put("success", true);
            response.put("token", token);
            response.put("patient", patient);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    /**
     * Patient registration endpoint
     * @param patientData Patient registration data
     * @return ResponseEntity with registration result
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerPatient(@RequestBody Map<String, Object> patientData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = patientData.get("email").toString();
            
            // Check if patient already exists
            if (patientService.existsByEmail(email)) {
                response.put("success", false);
                response.put("message", "Patient with this email already exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Create new patient
            Patient patient = new Patient();
            patient.setFirstName(patientData.get("firstName").toString());
            patient.setLastName(patientData.get("lastName").toString());
            patient.setEmail(email);
            patient.setPassword(patientData.get("password").toString());
            patient.setPhoneNumber(patientData.get("phoneNumber").toString());
            patient.setDateOfBirth(java.time.LocalDate.parse(patientData.get("dateOfBirth").toString()));
            patient.setAddress(patientData.get("address").toString());
            
            Patient savedPatient = patientService.savePatient(patient);
            
            response.put("success", true);
            response.put("patient", savedPatient);
            response.put("message", "Patient registered successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error registering patient: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Gets all patients (admin only)
     * @param token JWT token for authentication
     * @return ResponseEntity with patients
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPatients(@RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null) {
            response.put("success", false);
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        List<Patient> patients = patientService.getAllPatients();
        response.put("success", true);
        response.put("patients", patients);
        response.put("message", "Patients retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Gets patient by ID
     * @param patientId Patient ID
     * @param token JWT token for authentication
     * @return ResponseEntity with patient
     */
    @GetMapping("/{patientId}")
    public ResponseEntity<Map<String, Object>> getPatientById(
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
            Optional<Patient> patientOpt = patientService.findByEmail(email);
            if (patientOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Patient not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Patient patient = patientOpt.get();
            response.put("success", true);
            response.put("patient", patient);
            response.put("message", "Patient retrieved successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving patient: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
