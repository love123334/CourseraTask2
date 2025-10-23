# Smart Clinic Management System

A comprehensive healthcare management system built with Spring Boot, featuring separate portals for administrators, doctors, and patients.

## 🏥 System Overview

The Smart Clinic Management System is a full-stack web application that provides:

- **Admin Portal**: Manage doctors, patients, and system settings
- **Doctor Portal**: View appointments, manage patients, and handle prescriptions
- **Patient Portal**: Book appointments, view medical records, and access prescriptions

## 🚀 Features

### Core Functionality
- JWT-based authentication for all user types
- Appointment scheduling and management
- Digital prescription system
- Patient and doctor management
- Comprehensive reporting with stored procedures
- RESTful API with proper validation

### Technical Features
- Spring Boot backend with JPA/Hibernate
- MySQL database with stored procedures
- Docker containerization
- GitHub Actions CI/CD pipeline
- Responsive HTML/CSS/JavaScript frontend
- JWT token-based security

## 🛠️ Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **MySQL 8.0**
- **JWT (JSON Web Tokens)**
- **Maven**

### Frontend
- **HTML5**
- **CSS3**
- **JavaScript (ES6+)**
- **Responsive Design**

### DevOps
- **Docker**
- **GitHub Actions**
- **MySQL**

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher
- Docker (optional)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd smart-clinic-management
```

### 2. Database Setup
```sql
CREATE DATABASE smart_clinic_db;
USE smart_clinic_db;
```

### 3. Configure Application
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_clinic_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Run Stored Procedures
Execute the SQL file to create stored procedures:
```bash
mysql -u your_username -p smart_clinic_db < src/main/resources/sql/stored-procedures.sql
```

### 5. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

### 6. Access the Application
- **Main Portal**: http://localhost:8080
- **Admin Portal**: http://localhost:8080/admin-portal.html
- **Doctor Portal**: http://localhost:8080/doctor-portal.html
- **Patient Portal**: http://localhost:8080/patient-portal.html

## 🐳 Docker Deployment

### Build Docker Image
```bash
docker build -t smart-clinic-management:latest .
```

### Run with Docker
```bash
docker run -p 8080:8080 smart-clinic-management:latest
```

## 📊 Database Schema

### Core Tables
1. **doctors** - Doctor information and availability
2. **patients** - Patient information and medical history
3. **appointments** - Appointment scheduling and management
4. **prescriptions** - Digital prescription system

### Stored Procedures
- `GetDailyAppointmentReportByDoctor` - Daily appointment reports
- `GetDoctorWithMostPatientsByMonth` - Monthly patient statistics
- `GetDoctorWithMostPatientsByYear` - Yearly patient statistics

## 🔐 Authentication

### Default Credentials
- **Admin**: admin@smartclinic.com / admin123
- **Doctor**: Use registered doctor credentials
- **Patient**: Use registered patient credentials

## 📡 API Endpoints

### Authentication
- `POST /api/admin/login` - Admin login
- `POST /api/doctors/login` - Doctor login
- `POST /api/patients/login` - Patient login
- `POST /api/patients/register` - Patient registration

### Doctors
- `GET /api/doctors` - Get all doctors
- `GET /api/doctors/{id}/availability` - Get doctor availability
- `GET /api/doctors/search?name={name}` - Search doctors by name

### Appointments
- `POST /api/appointments` - Book appointment
- `GET /api/appointments/patient` - Get patient appointments
- `GET /api/appointments/doctor` - Get doctor appointments

### Prescriptions
- `POST /api/prescriptions` - Create prescription
- `GET /api/prescriptions/patient/{id}` - Get patient prescriptions
- `GET /api/prescriptions/doctor` - Get doctor prescriptions

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test API Endpoints
```bash
# Get all doctors
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/doctors

# Book appointment
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer <token>" \
  -d '{"doctorId": 1, "appointmentTime": "2024-01-15T10:00:00"}' \
  http://localhost:8080/api/appointments
```

## 📈 CI/CD Pipeline

The project includes GitHub Actions workflow for:
- Java compilation and testing
- Docker image building
- Automated deployment (on main branch)

## 🗂️ Project Structure

```
src/
├── main/
│   ├── java/com/smartclinic/
│   │   ├── controller/     # REST controllers
│   │   ├── entity/         # JPA entities
│   │   ├── repository/     # Data repositories
│   │   ├── service/       # Business logic
│   │   └── SmartClinicApplication.java
│   ├── resources/
│   │   ├── static/        # Frontend files
│   │   ├── sql/          # Database scripts
│   │   └── application.properties
│   └── test/
├── .github/workflows/     # CI/CD configuration
├── Dockerfile
├── pom.xml
└── README.md
```

## 🔧 Configuration

### Environment Variables
- `SPRING_DATASOURCE_URL` - Database URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `JWT_SECRET` - JWT signing secret
- `JWT_EXPIRATION` - Token expiration time

### Application Properties
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/smart_clinic_db
spring.datasource.username=root
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
jwt.secret=mySecretKey123456789012345678901234567890
jwt.expiration=86400000

# Server Configuration
server.port=8080
```

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📞 Support

For support and questions, please contact the development team or create an issue in the repository.

## 🎯 Future Enhancements

- Real-time notifications
- Mobile app integration
- Advanced analytics dashboard
- Integration with external healthcare systems
- Multi-language support
- Advanced reporting features

---

**Smart Clinic Management System** - Streamlining healthcare management with modern technology.
