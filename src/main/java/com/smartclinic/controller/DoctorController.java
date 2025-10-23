package com.smartclinic.controller;

import com.smartclinic.entity.Doctor;
import com.smartclinic.service.DoctorService;
import com.smartclinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private TokenService tokenService;
    
    /**
     * Exposes a GET endpoint for doctor availability using dynamic parameters
     * @param doctorId Doctor ID
     * @param date Date to check availability
     * @param token JWT token for authentication
     * @return ResponseEntity with available time slots
     */
    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<Map<String, Object>> getDoctorAvailability(
            @PathVariable Long doctorId,
            @RequestParam String date,
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
            LocalDate appointmentDate = LocalDate.parse(date);
            List<LocalTime> availableSlots = doctorService.getAvailableTimeSlots(doctorId, appointmentDate);
            
            response.put("success", true);
            response.put("doctorId", doctorId);
            response.put("date", date);
            response.put("availableSlots", availableSlots);
            response.put("message", "Available time slots retrieved successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving availability: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Gets all doctors
     * @param token JWT token for authentication
     * @return ResponseEntity with list of doctors
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllDoctors(@RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null) {
            response.put("success", false);
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        List<Doctor> doctors = doctorService.getAllDoctors();
        response.put("success", true);
        response.put("doctors", doctors);
        response.put("message", "Doctors retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Gets doctors by specialty
     * @param specialty Medical specialty
     * @param token JWT token for authentication
     * @return ResponseEntity with list of doctors
     */
    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<Map<String, Object>> getDoctorsBySpecialty(
            @PathVariable String specialty,
            @RequestHeader("Authorization") String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null) {
            response.put("success", false);
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        List<Doctor> doctors = doctorService.findBySpecialty(specialty);
        response.put("success", true);
        response.put("doctors", doctors);
        response.put("message", "Doctors retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Searches doctors by name
     * @param name Doctor's name
     * @param token JWT token for authentication
     * @return ResponseEntity with list of doctors
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDoctorsByName(
            @RequestParam String name,
            @RequestHeader("Authorization") String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Validate token
        String email = tokenService.validateToken(token.replace("Bearer ", ""));
        if (email == null) {
            response.put("success", false);
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        List<Doctor> doctors = doctorService.findByNameContaining(name);
        response.put("success", true);
        response.put("doctors", doctors);
        response.put("message", "Doctors retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Doctor login endpoint
     * @param loginRequest Login credentials
     * @return ResponseEntity with login result
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> doctorLogin(@RequestBody Map<String, String> loginRequest) {
        Map<String, Object> response = new HashMap<>();
        
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        
        Doctor doctor = doctorService.validateDoctorLogin(email, password);
        if (doctor != null) {
            String token = tokenService.generateToken(doctor.getEmail());
            response.put("success", true);
            response.put("token", token);
            response.put("doctor", doctor);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
