@echo off
REM Build script for Military Coordination System (LibGDX Multi-Module Project)
REM This script uses Maven to build the core and desktop modules

echo Building Military Coordination System...

REM Check if Maven is available
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed or not in PATH!
    echo Please install Maven to build this project.
    echo Alternatively, use VS Code with F5 to launch.
    pause
    exit /b 1
)

REM Build all modules using Maven
echo Building all modules with Maven...
mvn clean compile

if %errorlevel% neq 0 (
    echo Build failed!
    pause
    exit /b 1
)

echo Build complete!
echo.
echo Available launch options:
echo 1. Use VS Code: Press F5 and select "LibGDX Desktop - Release"
echo 2. Use Maven: mvn exec:java (from desktop directory)
echo 3. Use command line: java -cp "desktop\target\classes;core\target\classes;desktop\target\dependency\*" com.military.coordination.DesktopLauncher
echo.
pause
