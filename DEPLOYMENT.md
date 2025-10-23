# 🚀 Smart Clinic Management System - GitHub Deployment Guide

## 📋 **Step-by-Step GitHub Deployment**

### **Step 1: Create GitHub Repository**

1. Go to [GitHub.com](https://github.com) and sign in
2. Click the **"+"** button in the top right corner
3. Select **"New repository"**
4. Fill in the details:
   - **Repository name:** `smart-clinic-management-system`
   - **Description:** `A comprehensive healthcare management system built with Spring Boot, featuring separate portals for administrators, doctors, and patients.`
   - **Visibility:** Public (so you can share the links)
   - **Initialize:** Don't check any boxes (we already have files)

### **Step 2: Connect Local Repository to GitHub**

Run these commands in your terminal (in the project directory):

```bash
# Add the remote repository (replace YOUR_USERNAME with your GitHub username)
git remote add origin https://github.com/YOUR_USERNAME/smart-clinic-management-system.git

# Push to GitHub
git branch -M main
git push -u origin main
```

### **Step 3: Enable GitHub Pages (Optional)**

1. Go to your repository on GitHub
2. Click **Settings** tab
3. Scroll down to **Pages** section
4. Under **Source**, select **Deploy from a branch**
5. Select **main** branch and **/ (root)** folder
6. Click **Save**

### **Step 4: Create Issues for User Stories**

1. Go to your repository on GitHub
2. Click **Issues** tab
3. Click **New issue**
4. Copy the content from `github-issues.md` and create individual issues
5. Use appropriate labels: `admin`, `doctor`, `patient`, `backend`, `frontend`, etc.

## 🔗 **Public Links for Assignment**

Once deployed, you can provide these links:

### **Question 1: User Stories Issues**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/issues`

### **Question 2: Schema Design**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/schema-design.md`

### **Question 3: Doctor.java**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/entity/Doctor.java`

### **Question 4: Appointment.java**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/entity/Appointment.java`

### **Question 5: DoctorController.java**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/controller/DoctorController.java`

### **Question 6: AppointmentService.java**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/service/AppointmentService.java`

### **Question 7: PrescriptionController.java**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/controller/PrescriptionController.java`

### **Question 8: PatientRepository.java**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/repository/PatientRepository.java`

### **Question 9: TokenService.java**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/service/TokenService.java`

### **Question 10: DoctorService.java**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/src/main/java/com/smartclinic/service/DoctorService.java`

### **Question 11: Dockerfile**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/Dockerfile`

### **Question 12: GitHub Actions Workflow**
**Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system/blob/main/.github/workflows/ci.yml`

## 🎯 **Quick Setup Commands**

```bash
# 1. Initialize git (already done)
git init

# 2. Add all files (already done)
git add .

# 3. Commit (already done)
git commit -m "Initial commit: Smart Clinic Management System"

# 4. Create GitHub repository (do this manually on GitHub.com)

# 5. Add remote and push
git remote add origin https://github.com/YOUR_USERNAME/smart-clinic-management-system.git
git branch -M main
git push -u origin main
```

## 📱 **Frontend Portals (After Deployment)**

If you enable GitHub Pages, your portals will be available at:
- **Main Portal:** `https://YOUR_USERNAME.github.io/smart-clinic-management-system/`
- **Admin Portal:** `https://YOUR_USERNAME.github.io/smart-clinic-management-system/admin-portal.html`
- **Doctor Portal:** `https://YOUR_USERNAME.github.io/smart-clinic-management-system/doctor-portal.html`
- **Patient Portal:** `https://YOUR_USERNAME.github.io/smart-clinic-management-system/patient-portal.html`

## 🔧 **Local Development**

To run the application locally:

```bash
# 1. Start MySQL database
# 2. Run the application
mvn spring-boot:run

# 3. Access the application
# - Main: http://localhost:8080
# - Admin: http://localhost:8080/admin-portal.html
# - Doctor: http://localhost:8080/doctor-portal.html
# - Patient: http://localhost:8080/patient-portal.html
```

## 📊 **Database Setup**

1. Create MySQL database:
```sql
CREATE DATABASE smart_clinic_db;
USE smart_clinic_db;
```

2. Run the SQL scripts:
```bash
mysql -u root -p smart_clinic_db < src/main/resources/sql/stored-procedures.sql
mysql -u root -p smart_clinic_db < src/main/resources/sql/sample-data.sql
```

## 🧪 **Testing**

Run the test script:
```bash
./test-endpoints.sh
```

## 📝 **Assignment Submission**

Once deployed, you can provide the GitHub repository link and all the specific file links for your assignment. The repository will contain:

- ✅ Complete Spring Boot application
- ✅ All required Java files with proper annotations
- ✅ Database schema documentation
- ✅ User stories as GitHub issues
- ✅ Docker configuration
- ✅ GitHub Actions CI/CD
- ✅ Frontend portals
- ✅ Comprehensive documentation

**Repository Link:** `https://github.com/YOUR_USERNAME/smart-clinic-management-system`

---

**Note:** Replace `YOUR_USERNAME` with your actual GitHub username in all the links above.
