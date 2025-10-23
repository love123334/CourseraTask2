package com.smartclinic.service;

import com.smartclinic.entity.Appointment;
import com.smartclinic.entity.Doctor;
import com.smartclinic.entity.Patient;
import com.smartclinic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    /**
     * Implements a booking method that saves an appointment
     * @param appointment Appointment to save
     * @return Saved appointment
     */
    public Appointment bookAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    /**
     * Defines a method to retrieve appointments for a doctor on a specific date
     * @param doctor Doctor entity
     * @param date Specific date
     * @return List of appointments for the doctor on the given date
     */
    public List<Appointment> getAppointmentsForDoctorOnDate(Doctor doctor, LocalDate date) {
        return appointmentRepository.findByDoctorAndDate(doctor, date);
    }
    
    /**
     * Gets all appointments for a doctor
     * @param doctor Doctor entity
     * @return List of all appointments for the doctor
     */
    public List<Appointment> getAppointmentsForDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctor(doctor);
    }
    
    /**
     * Gets all appointments for a patient
     * @param patient Patient entity
     * @return List of all appointments for the patient
     */
    public List<Appointment> getAppointmentsForPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient);
    }
    
    /**
     * Gets future appointments for a patient
     * @param patient Patient entity
     * @return List of future appointments for the patient
     */
    public List<Appointment> getFutureAppointmentsForPatient(Patient patient) {
        return appointmentRepository.findByPatientAndAppointmentTimeAfter(patient, LocalDateTime.now());
    }
    
    /**
     * Gets appointments for a doctor within a time range
     * @param doctor Doctor entity
     * @param start Start time
     * @param end End time
     * @return List of appointments within the time range
     */
    public List<Appointment> getAppointmentsForDoctorInRange(Doctor doctor, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDoctorAndAppointmentTimeBetween(doctor, start, end);
    }
    
    /**
     * Checks if a doctor has an appointment at a specific time
     * @param doctor Doctor entity
     * @param appointmentTime Specific appointment time
     * @return List of appointments at that time (should be empty if available)
     */
    public List<Appointment> getAppointmentsAtTime(Doctor doctor, LocalDateTime appointmentTime) {
        return appointmentRepository.findByDoctorAndAppointmentTime(doctor, appointmentTime);
    }
    
    /**
     * Updates an appointment
     * @param appointment Appointment to update
     * @return Updated appointment
     */
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    /**
     * Deletes an appointment
     * @param appointmentId Appointment ID to delete
     */
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }
    
    /**
     * Gets an appointment by ID
     * @param appointmentId Appointment ID
     * @return Optional Appointment
     */
    public Optional<Appointment> getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }
}
