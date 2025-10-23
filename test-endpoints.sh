#!/bin/bash

# Smart Clinic Management System - API Testing Script
# This script tests all the major API endpoints

BASE_URL="http://localhost:8080"
ADMIN_EMAIL="admin@smartclinic.com"
ADMIN_PASSWORD="admin123"
DOCTOR_EMAIL="dr.smith@smartclinic.com"
DOCTOR_PASSWORD="password123"
PATIENT_EMAIL="alice.anderson@email.com"
PATIENT_PASSWORD="password123"

echo "🏥 Smart Clinic Management System - API Testing"
echo "=============================================="

# Function to make API calls and display results
test_endpoint() {
    local method=$1
    local url=$2
    local data=$3
    local headers=$4
    local description=$5
    
    echo ""
    echo "Testing: $description"
    echo "URL: $method $url"
    
    if [ -n "$data" ]; then
        echo "Data: $data"
    fi
    
    if [ -n "$headers" ]; then
        echo "Headers: $headers"
    fi
    
    echo "Response:"
    if [ -n "$data" ] && [ -n "$headers" ]; then
        curl -s -X $method "$url" -H "$headers" -d "$data" | jq '.' 2>/dev/null || curl -s -X $method "$url" -H "$headers" -d "$data"
    elif [ -n "$headers" ]; then
        curl -s -X $method "$url" -H "$headers" | jq '.' 2>/dev/null || curl -s -X $method "$url" -H "$headers"
    elif [ -n "$data" ]; then
        curl -s -X $method "$url" -d "$data" | jq '.' 2>/dev/null || curl -s -X $method "$url" -d "$data"
    else
        curl -s -X $method "$url" | jq '.' 2>/dev/null || curl -s -X $method "$url"
    fi
    echo "----------------------------------------"
}

# Test 1: Admin Login
echo "1. Testing Admin Login..."
ADMIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/admin/login" \
    -H "Content-Type: application/json" \
    -d "{\"email\":\"$ADMIN_EMAIL\",\"password\":\"$ADMIN_PASSWORD\"}")

ADMIN_TOKEN=$(echo $ADMIN_RESPONSE | jq -r '.token' 2>/dev/null)
echo "Admin Token: $ADMIN_TOKEN"
echo "Admin Response: $ADMIN_RESPONSE"

# Test 2: Doctor Login
echo ""
echo "2. Testing Doctor Login..."
DOCTOR_RESPONSE=$(curl -s -X POST "$BASE_URL/api/doctors/login" \
    -H "Content-Type: application/json" \
    -d "{\"email\":\"$DOCTOR_EMAIL\",\"password\":\"$DOCTOR_PASSWORD\"}")

DOCTOR_TOKEN=$(echo $DOCTOR_RESPONSE | jq -r '.token' 2>/dev/null)
echo "Doctor Token: $DOCTOR_TOKEN"
echo "Doctor Response: $DOCTOR_RESPONSE"

# Test 3: Patient Login
echo ""
echo "3. Testing Patient Login..."
PATIENT_RESPONSE=$(curl -s -X POST "$BASE_URL/api/patients/login" \
    -H "Content-Type: application/json" \
    -d "{\"email\":\"$PATIENT_EMAIL\",\"password\":\"$PATIENT_PASSWORD\"}")

PATIENT_TOKEN=$(echo $PATIENT_RESPONSE | jq -r '.token' 2>/dev/null)
echo "Patient Token: $PATIENT_TOKEN"
echo "Patient Response: $PATIENT_RESPONSE"

# Test 4: Get All Doctors (Admin)
if [ -n "$ADMIN_TOKEN" ]; then
    test_endpoint "GET" "$BASE_URL/api/admin/doctors" "" "Authorization: Bearer $ADMIN_TOKEN" "Get All Doctors (Admin)"
fi

# Test 5: Get All Doctors (Public)
test_endpoint "GET" "$BASE_URL/api/doctors" "" "Authorization: Bearer $ADMIN_TOKEN" "Get All Doctors (Public)"

# Test 6: Search Doctors by Name
test_endpoint "GET" "$BASE_URL/api/doctors/search?name=Smith" "" "Authorization: Bearer $ADMIN_TOKEN" "Search Doctors by Name"

# Test 7: Get Doctor Availability
test_endpoint "GET" "$BASE_URL/api/doctors/1/availability?date=2024-01-15" "" "Authorization: Bearer $ADMIN_TOKEN" "Get Doctor Availability"

# Test 8: Get All Patients (Admin)
if [ -n "$ADMIN_TOKEN" ]; then
    test_endpoint "GET" "$BASE_URL/api/admin/patients" "" "Authorization: Bearer $ADMIN_TOKEN" "Get All Patients (Admin)"
fi

# Test 9: Get Patient Appointments
if [ -n "$PATIENT_TOKEN" ]; then
    test_endpoint "GET" "$BASE_URL/api/appointments/patient" "" "Authorization: Bearer $PATIENT_TOKEN" "Get Patient Appointments"
fi

# Test 10: Get Doctor Appointments
if [ -n "$DOCTOR_TOKEN" ]; then
    test_endpoint "GET" "$BASE_URL/api/appointments/doctor" "" "Authorization: Bearer $DOCTOR_TOKEN" "Get Doctor Appointments"
fi

# Test 11: Book New Appointment (Patient)
if [ -n "$PATIENT_TOKEN" ]; then
    test_endpoint "POST" "$BASE_URL/api/appointments" \
        "{\"doctorId\":1,\"appointmentTime\":\"2024-01-20T10:00:00\",\"notes\":\"Test appointment\"}" \
        "Content-Type: application/json
Authorization: Bearer $PATIENT_TOKEN" \
        "Book New Appointment"
fi

# Test 12: Get Doctor Appointments by Date
if [ -n "$DOCTOR_TOKEN" ]; then
    test_endpoint "GET" "$BASE_URL/api/appointments/doctor/date/2024-01-15" "" "Authorization: Bearer $DOCTOR_TOKEN" "Get Doctor Appointments by Date"
fi

# Test 13: Get Patient Prescriptions
if [ -n "$PATIENT_TOKEN" ]; then
    test_endpoint "GET" "$BASE_URL/api/prescriptions/patient/1" "" "Authorization: Bearer $PATIENT_TOKEN" "Get Patient Prescriptions"
fi

# Test 14: Get Doctor Prescriptions
if [ -n "$DOCTOR_TOKEN" ]; then
    test_endpoint "GET" "$BASE_URL/api/prescriptions/doctor" "" "Authorization: Bearer $DOCTOR_TOKEN" "Get Doctor Prescriptions"
fi

# Test 15: Create New Prescription (Doctor)
if [ -n "$DOCTOR_TOKEN" ]; then
    test_endpoint "POST" "$BASE_URL/api/prescriptions" \
        "{\"patientId\":1,\"medicationName\":\"Test Medication\",\"dosage\":\"10mg\",\"instructions\":\"Take once daily\"}" \
        "Content-Type: application/json
Authorization: Bearer $DOCTOR_TOKEN" \
        "Create New Prescription"
fi

# Test 16: Add New Doctor (Admin)
if [ -n "$ADMIN_TOKEN" ]; then
    test_endpoint "POST" "$BASE_URL/api/admin/doctors" \
        "{\"firstName\":\"Test\",\"lastName\":\"Doctor\",\"email\":\"test.doctor@smartclinic.com\",\"password\":\"password123\",\"specialty\":\"General Medicine\",\"phoneNumber\":\"555-9999\",\"availableStartTime\":\"09:00:00\",\"availableEndTime\":\"17:00:00\"}" \
        "Content-Type: application/json
Authorization: Bearer $ADMIN_TOKEN" \
        "Add New Doctor"
fi

# Test 17: Patient Registration
test_endpoint "POST" "$BASE_URL/api/patients/register" \
    "{\"firstName\":\"Test\",\"lastName\":\"Patient\",\"email\":\"test.patient@email.com\",\"password\":\"password123\",\"phoneNumber\":\"555-8888\",\"dateOfBirth\":\"1990-01-01\",\"address\":\"123 Test St\"}" \
    "Content-Type: application/json" \
    "Patient Registration"

echo ""
echo "🎉 API Testing Complete!"
echo "========================"
echo ""
echo "Summary:"
echo "- Admin Login: $([ -n "$ADMIN_TOKEN" ] && echo "✅ Success" || echo "❌ Failed")"
echo "- Doctor Login: $([ -n "$DOCTOR_TOKEN" ] && echo "✅ Success" || echo "❌ Failed")"
echo "- Patient Login: $([ -n "$PATIENT_TOKEN" ] && echo "✅ Success" || echo "❌ Failed")"
echo ""
echo "All endpoints have been tested. Check the responses above for any errors."
echo ""
echo "🌐 Frontend Portals:"
echo "- Main Portal: $BASE_URL"
echo "- Admin Portal: $BASE_URL/admin-portal.html"
echo "- Doctor Portal: $BASE_URL/doctor-portal.html"
echo "- Patient Portal: $BASE_URL/patient-portal.html"
