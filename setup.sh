#!/bin/bash

# Smart Clinic Management System - Setup Script
# This script sets up the complete development environment

echo "🏥 Smart Clinic Management System - Setup Script"
echo "=============================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

# Check if MySQL is installed
if ! command -v mysql &> /dev/null; then
    echo "❌ MySQL is not installed. Please install MySQL 8.0 or higher."
    exit 1
fi

echo "✅ Prerequisites check passed"

# Create database
echo ""
echo "📊 Setting up database..."
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS smart_clinic_db;"
mysql -u root -p smart_clinic_db < src/main/resources/sql/stored-procedures.sql
mysql -u root -p smart_clinic_db < src/main/resources/sql/sample-data.sql
echo "✅ Database setup complete"

# Build the application
echo ""
echo "🔨 Building application..."
mvn clean compile
if [ $? -eq 0 ]; then
    echo "✅ Application compiled successfully"
else
    echo "❌ Application compilation failed"
    exit 1
fi

# Run tests
echo ""
echo "🧪 Running tests..."
mvn test
if [ $? -eq 0 ]; then
    echo "✅ Tests passed successfully"
else
    echo "⚠️  Some tests failed, but continuing..."
fi

# Package the application
echo ""
echo "📦 Packaging application..."
mvn package -DskipTests
if [ $? -eq 0 ]; then
    echo "✅ Application packaged successfully"
else
    echo "❌ Application packaging failed"
    exit 1
fi

# Build Docker image
echo ""
echo "🐳 Building Docker image..."
docker build -t smart-clinic-management:latest .
if [ $? -eq 0 ]; then
    echo "✅ Docker image built successfully"
else
    echo "❌ Docker image build failed"
    exit 1
fi

# Make test script executable
chmod +x test-endpoints.sh

echo ""
echo "🎉 Setup Complete!"
echo "=================="
echo ""
echo "Next steps:"
echo "1. Start the application: mvn spring-boot:run"
echo "2. Or run with Docker: docker run -p 8080:8080 smart-clinic-management:latest"
echo "3. Access the application at: http://localhost:8080"
echo "4. Test the API with: ./test-endpoints.sh"
echo ""
echo "🌐 Frontend Portals:"
echo "- Main Portal: http://localhost:8080"
echo "- Admin Portal: http://localhost:8080/admin-portal.html"
echo "- Doctor Portal: http://localhost:8080/doctor-portal.html"
echo "- Patient Portal: http://localhost:8080/patient-portal.html"
echo ""
echo "🔐 Default Credentials:"
echo "- Admin: admin@smartclinic.com / admin123"
echo "- Doctor: dr.smith@smartclinic.com / password123"
echo "- Patient: alice.anderson@email.com / password123"
echo ""
echo "📚 Documentation:"
echo "- README.md - Complete setup and usage guide"
echo "- schema-design.md - Database schema documentation"
echo "- github-issues.md - User stories and requirements"
echo ""
echo "Happy coding! 🚀"
