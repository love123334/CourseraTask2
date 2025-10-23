package com.smartclinic.repository;

import com.smartclinic.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    Optional<Doctor> findByEmail(String email);
    
    List<Doctor> findBySpecialty(String specialty);
    
    @Query("SELECT d FROM Doctor d WHERE d.specialty = :specialty AND d.availableStartTime <= :time AND d.availableEndTime >= :time")
    List<Doctor> findBySpecialtyAndAvailableTime(@Param("specialty") String specialty, @Param("time") String time);
    
    @Query("SELECT d FROM Doctor d WHERE d.firstName LIKE %:name% OR d.lastName LIKE %:name%")
    List<Doctor> findByNameContaining(@Param("name") String name);
    
    boolean existsByEmail(String email);
}
