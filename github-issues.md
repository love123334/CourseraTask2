# Smart Clinic Management System - GitHub Issues (User Stories)

## Issue #1: Admin User Stories

### As an Admin, I want to manage doctors so that I can maintain the clinic's medical staff.

**Acceptance Criteria:**
- [ ] Admin can add new doctors with complete information (name, email, specialty, phone, availability)
- [ ] Admin can view all doctors in the system
- [ ] Admin can update doctor information
- [ ] Admin can remove doctors from the system
- [ ] Admin can search doctors by name or specialty

**Priority:** High
**Story Points:** 8
**Labels:** admin, doctor-management, backend

---

### As an Admin, I want to view all patients so that I can monitor patient registrations.

**Acceptance Criteria:**
- [ ] Admin can view a list of all registered patients
- [ ] Admin can see patient contact information and registration date
- [ ] Admin can search patients by name or email
- [ ] Admin can view patient appointment history

**Priority:** Medium
**Story Points:** 5
**Labels:** admin, patient-management, backend

---

### As an Admin, I want to access system reports so that I can monitor clinic performance.

**Acceptance Criteria:**
- [ ] Admin can view daily appointment reports
- [ ] Admin can see doctor performance metrics
- [ ] Admin can access patient statistics
- [ ] Admin can generate monthly and yearly reports

**Priority:** Medium
**Story Points:** 13
**Labels:** admin, reporting, database

---

## Issue #2: Doctor User Stories

### As a Doctor, I want to view my appointments so that I can manage my schedule.

**Acceptance Criteria:**
- [ ] Doctor can see all their scheduled appointments
- [ ] Doctor can filter appointments by date
- [ ] Doctor can view appointment details (patient, time, notes)
- [ ] Doctor can update appointment status (confirmed, completed, cancelled)

**Priority:** High
**Story Points:** 8
**Labels:** doctor, appointments, frontend

---

### As a Doctor, I want to manage prescriptions so that I can provide medication to patients.

**Acceptance Criteria:**
- [ ] Doctor can create new prescriptions for patients
- [ ] Doctor can view all prescriptions they've written
- [ ] Doctor can include medication name, dosage, and instructions
- [ ] Doctor can search prescriptions by patient or medication

**Priority:** High
**Story Points:** 8
**Labels:** doctor, prescriptions, backend

---

### As a Doctor, I want to view my availability so that I can manage my schedule.

**Acceptance Criteria:**
- [ ] Doctor can see their available time slots
- [ ] Doctor can view booked appointments
- [ ] Doctor can see their working hours
- [ ] Doctor can identify free time slots for new appointments

**Priority:** Medium
**Story Points:** 5
**Labels:** doctor, availability, frontend

---

## Issue #3: Patient User Stories

### As a Patient, I want to find doctors so that I can book appointments.

**Acceptance Criteria:**
- [ ] Patient can search doctors by name
- [ ] Patient can search doctors by specialty
- [ ] Patient can view doctor information (name, specialty, phone, availability)
- [ ] Patient can see doctor availability for booking

**Priority:** High
**Story Points:** 8
**Labels:** patient, doctor-search, frontend

---

### As a Patient, I want to book appointments so that I can schedule medical consultations.

**Acceptance Criteria:**
- [ ] Patient can select a doctor and available time slot
- [ ] Patient can provide appointment notes
- [ ] Patient can view appointment confirmation
- [ ] Patient can see appointment details after booking

**Priority:** High
**Story Points:** 8
**Labels:** patient, appointments, backend

---

### As a Patient, I want to view my appointments so that I can track my medical schedule.

**Acceptance Criteria:**
- [ ] Patient can see all their scheduled appointments
- [ ] Patient can view appointment details (doctor, time, status)
- [ ] Patient can see appointment status (scheduled, confirmed, completed, cancelled)
- [ ] Patient can view appointment notes

**Priority:** High
**Story Points:** 5
**Labels:** patient, appointments, frontend

---

### As a Patient, I want to view my prescriptions so that I can manage my medications.

**Acceptance Criteria:**
- [ ] Patient can see all prescriptions from their doctors
- [ ] Patient can view medication details (name, dosage, instructions)
- [ ] Patient can see prescription dates
- [ ] Patient can view which doctor prescribed each medication

**Priority:** Medium
**Story Points:** 5
**Labels:** patient, prescriptions, frontend

---

### As a Patient, I want to register an account so that I can access the patient portal.

**Acceptance Criteria:**
- [ ] Patient can create account with personal information
- [ ] Patient can provide contact details (email, phone, address)
- [ ] Patient can set secure password
- [ ] Patient can login with email and password

**Priority:** High
**Story Points:** 5
**Labels:** patient, authentication, backend

---

## Issue #4: System User Stories

### As a System, I want to authenticate users so that I can provide secure access.

**Acceptance Criteria:**
- [ ] System can validate user credentials
- [ ] System can generate JWT tokens for authenticated users
- [ ] System can validate tokens for protected endpoints
- [ ] System can handle token expiration

**Priority:** High
**Story Points:** 8
**Labels:** authentication, security, backend

---

### As a System, I want to store appointment data so that I can manage scheduling.

**Acceptance Criteria:**
- [ ] System can store appointment details (doctor, patient, time, status)
- [ ] System can prevent double-booking of time slots
- [ ] System can validate appointment times
- [ ] System can track appointment status changes

**Priority:** High
**Story Points:** 8
**Labels:** appointments, database, backend

---

### As a System, I want to generate reports so that I can provide analytics.

**Acceptance Criteria:**
- [ ] System can generate daily appointment reports
- [ ] System can calculate doctor performance metrics
- [ ] System can provide patient statistics
- [ ] System can generate monthly and yearly reports

**Priority:** Medium
**Story Points:** 13
**Labels:** reporting, database, stored-procedures

---

## Issue #5: Technical User Stories

### As a Developer, I want to containerize the application so that I can deploy it easily.

**Acceptance Criteria:**
- [ ] Application can be built into Docker image
- [ ] Docker image includes all dependencies
- [ ] Application can run in containerized environment
- [ ] Docker configuration is optimized for production

**Priority:** Medium
**Story Points:** 5
**Labels:** docker, deployment, devops

---

### As a Developer, I want to implement CI/CD so that I can automate deployments.

**Acceptance Criteria:**
- [ ] GitHub Actions workflow compiles Java backend
- [ ] Workflow runs tests automatically
- [ ] Workflow builds Docker image
- [ ] Workflow can deploy to production

**Priority:** Medium
**Story Points:** 8
**Labels:** ci-cd, github-actions, devops

---

### As a Developer, I want to create stored procedures so that I can optimize database queries.

**Acceptance Criteria:**
- [ ] Stored procedure for daily appointment reports
- [ ] Stored procedure for doctor performance metrics
- [ ] Stored procedure for patient statistics
- [ ] Stored procedures are optimized for performance

**Priority:** Medium
**Story Points:** 8
**Labels:** database, stored-procedures, optimization

---

## Issue #6: Database User Stories

### As a Database, I want to maintain data integrity so that I can ensure data consistency.

**Acceptance Criteria:**
- [ ] Foreign key relationships are properly defined
- [ ] Data validation constraints are in place
- [ ] Unique constraints prevent duplicate data
- [ ] Referential integrity is maintained

**Priority:** High
**Story Points:** 8
**Labels:** database, integrity, constraints

---

### As a Database, I want to support efficient queries so that I can provide good performance.

**Acceptance Criteria:**
- [ ] Appropriate indexes are created for frequently queried fields
- [ ] Foreign key indexes are optimized
- [ ] Query performance is monitored
- [ ] Database schema is normalized

**Priority:** Medium
**Story Points:** 5
**Labels:** database, performance, optimization

---

## Summary

**Total Issues:** 16
**Total Story Points:** 140
**High Priority:** 8 issues (80 points)
**Medium Priority:** 8 issues (60 points)

**Key Features Covered:**
- ✅ Admin portal with doctor and patient management
- ✅ Doctor portal with appointment and prescription management
- ✅ Patient portal with appointment booking and medical records
- ✅ JWT-based authentication system
- ✅ RESTful API with proper validation
- ✅ MySQL database with stored procedures
- ✅ Docker containerization
- ✅ GitHub Actions CI/CD pipeline
- ✅ Responsive frontend with HTML/CSS/JavaScript

**Technical Stack:**
- Backend: Spring Boot, JPA, MySQL, JWT
- Frontend: HTML5, CSS3, JavaScript
- DevOps: Docker, GitHub Actions
- Database: MySQL with stored procedures
