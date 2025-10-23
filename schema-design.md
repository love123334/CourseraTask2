# Smart Clinic Management System - Database Schema Design

## Overview
This document describes the MySQL database design for the Smart Clinic Management System. The database consists of four main tables with appropriate relationships and constraints.

## Database Tables

### 1. Doctors Table
```sql
CREATE TABLE doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    available_start_time TIME NOT NULL,
    available_end_time TIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

**Fields:**
- `id`: Primary key, auto-increment
- `first_name`: Doctor's first name (VARCHAR(100), NOT NULL)
- `last_name`: Doctor's last name (VARCHAR(100), NOT NULL)
- `email`: Unique email address (VARCHAR(255), NOT NULL, UNIQUE)
- `password`: Encrypted password (VARCHAR(255), NOT NULL)
- `specialty`: Medical specialty (VARCHAR(100), NOT NULL)
- `phone_number`: Contact phone number (VARCHAR(20), NOT NULL)
- `available_start_time`: Start of working hours (TIME, NOT NULL)
- `available_end_time`: End of working hours (TIME, NOT NULL)
- `created_at`: Record creation timestamp
- `updated_at`: Record update timestamp

### 2. Patients Table
```sql
CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    address TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

**Fields:**
- `id`: Primary key, auto-increment
- `first_name`: Patient's first name (VARCHAR(100), NOT NULL)
- `last_name`: Patient's last name (VARCHAR(100), NOT NULL)
- `email`: Unique email address (VARCHAR(255), NOT NULL, UNIQUE)
- `password`: Encrypted password (VARCHAR(255), NOT NULL)
- `phone_number`: Contact phone number (VARCHAR(20), NOT NULL)
- `date_of_birth`: Patient's date of birth (DATE, NOT NULL)
- `address`: Patient's address (TEXT, NOT NULL)
- `created_at`: Record creation timestamp
- `updated_at`: Record update timestamp

### 3. Appointments Table
```sql
CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_time DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'SCHEDULED',
    notes TEXT,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);
```

**Fields:**
- `id`: Primary key, auto-increment
- `appointment_time`: Scheduled appointment time (DATETIME, NOT NULL)
- `status`: Appointment status (VARCHAR(50), NOT NULL, DEFAULT 'SCHEDULED')
- `notes`: Additional notes (TEXT)
- `doctor_id`: Foreign key to doctors table (BIGINT, NOT NULL)
- `patient_id`: Foreign key to patients table (BIGINT, NOT NULL)
- `created_at`: Record creation timestamp
- `updated_at`: Record update timestamp

**Foreign Key Relationships:**
- `doctor_id` → `doctors(id)` ON DELETE CASCADE
- `patient_id` → `patients(id)` ON DELETE CASCADE

### 4. Prescriptions Table
```sql
CREATE TABLE prescriptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    medication_name VARCHAR(255) NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    instructions TEXT NOT NULL,
    prescription_date DATETIME NOT NULL,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);
```

**Fields:**
- `id`: Primary key, auto-increment
- `medication_name`: Name of the medication (VARCHAR(255), NOT NULL)
- `dosage`: Medication dosage (VARCHAR(100), NOT NULL)
- `instructions`: Prescription instructions (TEXT, NOT NULL)
- `prescription_date`: Date when prescription was issued (DATETIME, NOT NULL)
- `doctor_id`: Foreign key to doctors table (BIGINT, NOT NULL)
- `patient_id`: Foreign key to patients table (BIGINT, NOT NULL)
- `created_at`: Record creation timestamp
- `updated_at`: Record update timestamp

**Foreign Key Relationships:**
- `doctor_id` → `doctors(id)` ON DELETE CASCADE
- `patient_id` → `patients(id)` ON DELETE CASCADE

## Additional Tables

### 5. Doctor Available Times Table
```sql
CREATE TABLE doctor_available_times (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    available_time TIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);
```

**Fields:**
- `id`: Primary key, auto-increment
- `doctor_id`: Foreign key to doctors table (BIGINT, NOT NULL)
- `available_time`: Specific available time slot (TIME, NOT NULL)
- `created_at`: Record creation timestamp

**Foreign Key Relationships:**
- `doctor_id` → `doctors(id)` ON DELETE CASCADE

## Indexes

### Primary Indexes
- All tables have primary key indexes on `id` fields
- Unique indexes on email fields in `doctors` and `patients` tables

### Foreign Key Indexes
- Index on `doctor_id` in `appointments` table
- Index on `patient_id` in `appointments` table
- Index on `doctor_id` in `prescriptions` table
- Index on `patient_id` in `prescriptions` table
- Index on `doctor_id` in `doctor_available_times` table

### Performance Indexes
- Index on `appointment_time` in `appointments` table for date-based queries
- Index on `prescription_date` in `prescriptions` table for date-based queries
- Index on `specialty` in `doctors` table for specialty-based searches

## Database Constraints

### NOT NULL Constraints
- All primary key fields
- All foreign key fields
- Essential business fields (names, emails, passwords, etc.)

### UNIQUE Constraints
- Email addresses in `doctors` and `patients` tables
- Composite unique constraint on `doctor_id` and `appointment_time` in `appointments` table

### CHECK Constraints
- `appointment_time` must be in the future
- `available_start_time` must be before `available_end_time`
- `status` must be one of: 'SCHEDULED', 'CONFIRMED', 'CANCELLED', 'COMPLETED'

## Relationships Summary

1. **One-to-Many Relationships:**
   - Doctor → Appointments (1:N)
   - Doctor → Prescriptions (1:N)
   - Patient → Appointments (1:N)
   - Patient → Prescriptions (1:N)
   - Doctor → Available Times (1:N)

2. **Many-to-Many Relationships:**
   - Doctor ↔ Patient (through Appointments)
   - Doctor ↔ Patient (through Prescriptions)

## Data Types and Sizes

- **BIGINT**: For all ID fields (supports large datasets)
- **VARCHAR(100)**: For names and short text fields
- **VARCHAR(255)**: For email addresses and medication names
- **TEXT**: For addresses and instructions
- **DATETIME**: For appointment and prescription timestamps
- **TIME**: For doctor availability times
- **DATE**: For patient date of birth
- **TIMESTAMP**: For audit fields

This schema design ensures data integrity, supports efficient querying, and provides a solid foundation for the Smart Clinic Management System.
