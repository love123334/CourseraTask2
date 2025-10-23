package com.smartclinic.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;
    
    @NotBlank(message = "Specialty is required")
    @Column(name = "specialty", nullable = false)
    private String specialty;
    
    @NotBlank(message = "Phone number is required")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    
    @NotNull(message = "Available start time is required")
    @Column(name = "available_start_time", nullable = false)
    private LocalTime availableStartTime;
    
    @NotNull(message = "Available end time is required")
    @Column(name = "available_end_time", nullable = false)
    private LocalTime availableEndTime;
    
    @ElementCollection
    @CollectionTable(name = "doctor_available_times", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "available_time")
    private List<LocalTime> availableTimes;
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;
    
    // Constructors
    public Doctor() {}
    
    public Doctor(String firstName, String lastName, String email, String password, 
                  String specialty, String phoneNumber, LocalTime availableStartTime, 
                  LocalTime availableEndTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.specialty = specialty;
        this.phoneNumber = phoneNumber;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSpecialty() {
        return specialty;
    }
    
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public LocalTime getAvailableStartTime() {
        return availableStartTime;
    }
    
    public void setAvailableStartTime(LocalTime availableStartTime) {
        this.availableStartTime = availableStartTime;
    }
    
    public LocalTime getAvailableEndTime() {
        return availableEndTime;
    }
    
    public void setAvailableEndTime(LocalTime availableEndTime) {
        this.availableEndTime = availableEndTime;
    }
    
    public List<LocalTime> getAvailableTimes() {
        return availableTimes;
    }
    
    public void setAvailableTimes(List<LocalTime> availableTimes) {
        this.availableTimes = availableTimes;
    }
    
    public List<Appointment> getAppointments() {
        return appointments;
    }
    
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
