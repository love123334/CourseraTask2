@echo off
REM Smart Clinic Management System - Windows Setup Script

echo 🏥 Smart Clinic Management System - Setup Script
echo ==============================================

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed. Please install Java 17 or higher.
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven is not installed. Please install Maven 3.6 or higher.
    exit /b 1
)

echo ✅ Prerequisites check passed

REM Build the application
echo.
echo 🔨 Building application...
mvn clean compile
if %errorlevel% neq 0 (
    echo ❌ Application compilation failed
    exit /b 1
)
echo ✅ Application compiled successfully

REM Run tests
echo.
echo 🧪 Running tests...
mvn test
if %errorlevel% neq 0 (
    echo ⚠️  Some tests failed, but continuing...
)

REM Package the application
echo.
echo 📦 Packaging application...
mvn package -DskipTests
if %errorlevel% neq 0 (
    echo ❌ Application packaging failed
    exit /b 1
)
echo ✅ Application packaged successfully

echo.
echo 🎉 Setup Complete!
echo ==================
echo.
echo Next steps:
echo 1. Start the application: mvn spring-boot:run
echo 2. Access the application at: http://localhost:8080
echo 3. Test the API with: test-endpoints.sh
echo.
echo 🌐 Frontend Portals:
echo - Main Portal: http://localhost:8080
echo - Admin Portal: http://localhost:8080/admin-portal.html
echo - Doctor Portal: http://localhost:8080/doctor-portal.html
echo - Patient Portal: http://localhost:8080/patient-portal.html
echo.
echo 🔐 Default Credentials:
echo - Admin: admin@smartclinic.com / admin123
echo - Doctor: dr.smith@smartclinic.com / password123
echo - Patient: alice.anderson@email.com / password123
echo.
echo 📚 Documentation:
echo - README.md - Complete setup and usage guide
echo - schema-design.md - Database schema documentation
echo - github-issues.md - User stories and requirements
echo.
echo Happy coding! 🚀
pause
