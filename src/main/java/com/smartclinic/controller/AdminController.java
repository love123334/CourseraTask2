package com.smartclinic.controller;

import com.smartclinic.entity.Doctor;
import com.smartclinic.entity.Patient;
import com.smartclinic.service.DoctorService;
import com.smartclinic.service.PatientService;
import com.smartclinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private TokenService tokenService;
    
    /**
     * Admin login endpoint
     * @param loginRequest Login credentials
     * @return ResponseEntity with login result
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> adminLogin(@RequestBody Map<String, String> loginRequest) {
        Map<String, Object> response = new HashMap<>();
        
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        
        // Simple admin credentials (in production, use proper authentication)
        if ("admin@smartclinic.com".equals(email) && "admin123".equals(password)) {
            String token = tokenService.generateToken(email);
            response.put("success", true);
            response.put("token", token);
            response.put("message", "Admin login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid admin credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    /**
     * Adds a new doctor to the system
     * @param doctorData Doctor data
     * @param token JWT token for authentication
     * @return ResponseEntity with doctor creation result
     */
    @PostMapping("/doctors")
    public ResponseEntity<Map<String, Object>> addDoctor(
            @RequestBody Map<String, Object> doctorData,
            @RequestHeader("Authorization") String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null || !"admin@smartclinic.com".equals(email)) {
            response.put("success", false);
            response.put("message", "Invalid or expired admin token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            String doctorEmail = doctorData.get("email").toString();
            
            // Check if doctor already exists
            if (doctorService.existsByEmail(doctorEmail)) {
                response.put("success", false);
                response.put("message", "Doctor with this email already exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Create new doctor
            Doctor doctor = new Doctor();
            doctor.setFirstName(doctorData.get("firstName").toString());
            doctor.setLastName(doctorData.get("lastName").toString());
            doctor.setEmail(doctorEmail);
            doctor.setPassword(doctorData.get("password").toString());
            doctor.setSpecialty(doctorData.get("specialty").toString());
            doctor.setPhoneNumber(doctorData.get("phoneNumber").toString());
            doctor.setAvailableStartTime(LocalTime.parse(doctorData.get("availableStartTime").toString()));
            doctor.setAvailableEndTime(LocalTime.parse(doctorData.get("availableEndTime").toString()));
            
            Doctor savedDoctor = doctorService.saveDoctor(doctor);
            
            response.put("success", true);
            response.put("doctor", savedDoctor);
            response.put("message", "Doctor added successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error adding doctor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Gets all doctors
     * @param token JWT token for authentication
     * @return ResponseEntity with doctors
     */
    @GetMapping("/doctors")
    public ResponseEntity<Map<String, Object>> getAllDoctors(@RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null || !"admin@smartclinic.com".equals(email)) {
            response.put("success", false);
            response.put("message", "Invalid or expired admin token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        List<Doctor> doctors = doctorService.getAllDoctors();
        response.put("success", true);
        response.put("doctors", doctors);
        response.put("message", "Doctors retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Gets all patients
     * @param token JWT token for authentication
     * @return ResponseEntity with patients
     */
    @GetMapping("/patients")
    public ResponseEntity<Map<String, Object>> getAllPatients(@RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null || !"admin@smartclinic.com".equals(email)) {
            response.put("success", false);
            response.put("message", "Invalid or expired admin token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        List<Patient> patients = patientService.getAllPatients();
        response.put("success", true);
        response.put("patients", patients);
        response.put("message", "Patients retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Updates a doctor
     * @param doctorId Doctor ID
     * @param doctorData Updated doctor data
     * @param token JWT token for authentication
     * @return ResponseEntity with update result
     */
    @PutMapping("/doctors/{doctorId}")
    public ResponseEntity<Map<String, Object>> updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody Map<String, Object> doctorData,
            @RequestHeader("Authorization") String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null || !"admin@smartclinic.com".equals(email)) {
            response.put("success", false);
            response.put("message", "Invalid or expired admin token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        try {
            Optional<Doctor> doctorOpt = doctorService.findByEmail(doctorService.getAllDoctors().get(doctorId.intValue() - 1).getEmail());
            if (doctorOpt.isPresent() == false) {
                response.put("success", false);
                response.put("message", "Doctor not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Doctor doctor = doctorOpt.get();
            
            // Update doctor fields
            if (doctorData.containsKey("firstName")) {
                doctor.setFirstName(doctorData.get("firstName").toString());
            }
            if (doctorData.containsKey("lastName")) {
                doctor.setLastName(doctorData.get("lastName").toString());
            }
            if (doctorData.containsKey("specialty")) {
                doctor.setSpecialty(doctorData.get("specialty").toString());
            }
            if (doctorData.containsKey("phoneNumber")) {
                doctor.setPhoneNumber(doctorData.get("phoneNumber").toString());
            }
            if (doctorData.containsKey("availableStartTime")) {
                doctor.setAvailableStartTime(LocalTime.parse(doctorData.get("availableStartTime").toString()));
            }
            if (doctorData.containsKey("availableEndTime")) {
                doctor.setAvailableEndTime(LocalTime.parse(doctorData.get("availableEndTime").toString()));
            }
            
            Doctor updatedDoctor = doctorService.saveDoctor(doctor);
            
            response.put("success", true);
            response.put("doctor", updatedDoctor);
            response.put("message", "Doctor updated successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating doctor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
