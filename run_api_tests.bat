@echo off
echo ==========================================
echo 🚀 API Automation Framework - RestAssured
echo ==========================================
echo.
echo 🔧 Step 1: Cleaning and Running API Tests...
call mvn clean test

echo.
echo 📊 Step 2: Generating and Opening Allure Report...
call allure serve allure-results

echo.
echo ✅ Done!
pause