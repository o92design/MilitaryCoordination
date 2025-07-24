@echo off
echo Building and Running LibGDX Military Coordination System...
echo.

REM Check if Maven is available
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed or not in PATH!
    echo Please install Maven or use VS Code with F5 to launch.
    pause
    exit /b 1
)

echo [1/3] Building project with Maven...
mvn clean compile dependency:copy-dependencies -f desktop\pom.xml

if %errorlevel% neq 0 (
    echo Build failed!
    pause
    exit /b 1
)

echo [2/3] Build successful!
echo [3/3] Running LibGDX Desktop application...
echo.

REM Run the LibGDX desktop launcher
java -cp "desktop\target\classes;core\target\classes;desktop\target\dependency\*" com.military.coordination.DesktopLauncher

echo.
echo Application finished.
pause
