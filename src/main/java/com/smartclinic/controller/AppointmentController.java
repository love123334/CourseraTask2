package com.smartclinic.controller;

import com.smartclinic.entity.Appointment;
import com.smartclinic.entity.Doctor;
import com.smartclinic.entity.Patient;
import com.smartclinic.service.AppointmentService;
import com.smartclinic.service.DoctorService;
import com.smartclinic.service.PatientService;
import com.smartclinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private TokenService tokenService;
    
    /**
     * Books a new appointment
     * @param appointmentRequest Appointment data
     * @param token JWT token for authentication
     * @return ResponseEntity with appointment result
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> bookAppointment(
            @RequestBody Map<String, Object> appointmentRequest,
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
            // Get patient from token
            Optional<Patient> patientOpt = patientService.findByEmail(email);
            if (patientOpt.isPresent() == false) {
                response.put("success", false);
                response.put("message", "Patient not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Patient patient = patientOpt.get();
            
            // Get doctor
            Long doctorId = Long.valueOf(appointmentRequest.get("doctorId").toString());
            Optional<Doctor> doctorOpt = doctorService.findByEmail(doctorService.getAllDoctors().get(doctorId.intValue() - 1).getEmail());
            if (doctorOpt.isPresent() == false) {
                response.put("success", false);
                response.put("message", "Doctor not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Doctor doctor = doctorOpt.get();
            
            // Parse appointment time
            String appointmentTimeStr = appointmentRequest.get("appointmentTime").toString();
            LocalDateTime appointmentTime = LocalDateTime.parse(appointmentTimeStr);
            
            // Check if doctor is available at that time
            List<Appointment> existingAppointments = appointmentService.getAppointmentsAtTime(doctor, appointmentTime);
            if (!existingAppointments.isEmpty()) {
                response.put("success", false);
                response.put("message", "Doctor is not available at the requested time");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Create appointment
            Appointment appointment = new Appointment();
            appointment.setAppointmentTime(appointmentTime);
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setStatus("SCHEDULED");
            
            if (appointmentRequest.containsKey("notes")) {
                appointment.setNotes(appointmentRequest.get("notes").toString());
            }
            
            Appointment savedAppointment = appointmentService.bookAppointment(appointment);
            
            response.put("success", true);
            response.put("appointment", savedAppointment);
            response.put("message", "Appointment booked successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error booking appointment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Gets appointments for a patient
     * @param token JWT token for authentication
     * @return ResponseEntity with appointments
     */
    @GetMapping("/patient")
    public ResponseEntity<Map<String, Object>> getAppointmentsForPatient(
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
            if (patientOpt.isPresent() == false) {
                response.put("success", false);
                response.put("message", "Patient not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Patient patient = patientOpt.get();
            List<Appointment> appointments = appointmentService.getAppointmentsForPatient(patient);
            
            response.put("success", true);
            response.put("appointments", appointments);
            response.put("message", "Appointments retrieved successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving appointments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Gets appointments for a doctor
     * @param token JWT token for authentication
     * @return ResponseEntity with appointments
     */
    @GetMapping("/doctor")
    public ResponseEntity<Map<String, Object>> getAppointmentsForDoctor(
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
            if (doctorOpt.isPresent() == false) {
                response.put("success", false);
                response.put("message", "Doctor not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Doctor doctor = doctorOpt.get();
            List<Appointment> appointments = appointmentService.getAppointmentsForDoctor(doctor);
            
            response.put("success", true);
            response.put("appointments", appointments);
            response.put("message", "Appointments retrieved successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving appointments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Gets appointments for a doctor on a specific date
     * @param date Date to filter appointments
     * @param token JWT token for authentication
     * @return ResponseEntity with appointments
     */
    @GetMapping("/doctor/date/{date}")
    public ResponseEntity<Map<String, Object>> getAppointmentsForDoctorOnDate(
            @PathVariable String date,
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
            if (doctorOpt.isPresent() == false) {
                response.put("success", false);
                response.put("message", "Doctor not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Doctor doctor = doctorOpt.get();
            LocalDate appointmentDate = LocalDate.parse(date);
            List<Appointment> appointments = appointmentService.getAppointmentsForDoctorOnDate(doctor, appointmentDate);
            
            response.put("success", true);
            response.put("appointments", appointments);
            response.put("message", "Appointments retrieved successfully");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving appointments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
