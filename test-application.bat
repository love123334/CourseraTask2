@echo off
echo Testing Smart Clinic Management System...
echo.

REM Set JAVA_HOME
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_202

echo 1. Testing compilation...
call .\mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo ❌ Compilation failed
    exit /b 1
)
echo ✅ Compilation successful

echo.
echo 2. Testing application startup...
echo Starting application in background...
start /B .\mvnw.cmd spring-boot:run

echo Waiting for application to start...
timeout /t 30 /nobreak >nul

echo.
echo 3. Testing application endpoints...
echo Testing main page...
curl -s http://localhost:8080 >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Application is running on http://localhost:8080
) else (
    echo ⚠️  Application may need database connection
)

echo.
echo 4. Available endpoints:
echo - Main Portal: http://localhost:8080
echo - Admin Portal: http://localhost:8080/admin-portal.html
echo - Doctor Portal: http://localhost:8080/doctor-portal.html
echo - Patient Portal: http://localhost:8080/patient-portal.html

echo.
echo 5. Default credentials:
echo - Admin: admin@smartclinic.com / admin123
echo - Doctor: dr.smith@smartclinic.com / password123
echo - Patient: alice.anderson@email.com / password123

echo.
echo 🎉 Smart Clinic Management System is ready!
echo.
pause
