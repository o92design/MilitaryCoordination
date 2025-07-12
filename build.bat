@echo off
REM Simple build script for Military Coordination System
REM This script compiles and runs tests without requiring Maven

echo Building Military Coordination System...

REM Clean previous build (if exists)
if exist "target" (
    echo Cleaning previous build...
    rmdir /s /q target
)

REM Create output directories
if not exist "target\classes" mkdir target\classes
if not exist "target\test-classes" mkdir target\test-classes

REM Compile main source files
echo Compiling main sources...
javac -d target\classes -cp "src\main\java" src\main\java\com\military\coordination\model\*.java src\main\java\com\military\coordination\core\*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

REM Note: To run tests, you'll need to download JUnit 5 JARs manually
REM or use an IDE with built-in test runner

echo Build complete!
echo.
echo To run the application: java -cp target\classes com.military.coordination.core.Main
echo To run tests, use your IDE or install Maven.
echo For VS Code: Install "Extension Pack for Java" and use the Test Explorer.
echo For IntelliJ IDEA: Right-click on test files and select "Run Tests".
echo.
pause
