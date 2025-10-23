package com.smartclinic.service;

import com.smartclinic.entity.Doctor;
import com.smartclinic.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    /**
     * Returns available time slots for doctor on a given date
     * @param doctorId Doctor ID
     * @param date Date to check availability
     * @return List of available time slots
     */
    public List<LocalTime> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isPresent() == false) {
            return new ArrayList<>();
        }
        
        Doctor doctor = doctorOpt.get();
        List<LocalTime> availableSlots = new ArrayList<>();
        
        // Generate time slots between doctor's available hours
        LocalTime startTime = doctor.getAvailableStartTime();
        LocalTime endTime = doctor.getAvailableEndTime();
        
        LocalTime currentTime = startTime;
        while (currentTime.isBefore(endTime)) {
            availableSlots.add(currentTime);
            currentTime = currentTime.plusHours(1); // 1-hour slots
        }
        
        return availableSlots;
    }
    
    /**
     * Validates doctor login credentials and returns structured response
     * @param email Doctor's email
     * @param password Doctor's password
     * @return Doctor object if credentials are valid, null otherwise
     */
    public Doctor validateDoctorLogin(String email, String password) {
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            if (doctor.getPassword().equals(password)) {
                return doctor;
            }
        }
        return null;
    }
    
    /**
     * Saves a new doctor
     * @param doctor Doctor entity to save
     * @return Saved doctor
     */
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    
    /**
     * Finds doctor by email
     * @param email Doctor's email
     * @return Optional Doctor
     */
    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
    
    /**
     * Finds doctors by specialty
     * @param specialty Medical specialty
     * @return List of doctors with the specialty
     */
    public List<Doctor> findBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }
    
    /**
     * Finds doctors by name
     * @param name Doctor's name (first or last)
     * @return List of doctors matching the name
     */
    public List<Doctor> findByNameContaining(String name) {
        return doctorRepository.findByNameContaining(name);
    }
    
    /**
     * Gets all doctors
     * @return List of all doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    /**
     * Checks if doctor exists by email
     * @param email Doctor's email
     * @return true if exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return doctorRepository.existsByEmail(email);
    }
}
