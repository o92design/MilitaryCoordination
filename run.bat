@echo off
echo Building and Running Military Coordination System...
echo.

echo [1/3] Compiling Java sources...
javac -cp src/main/java src/main/java/com/military/coordination/model/*.java src/main/java/com/military/coordination/core/Main.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo [2/3] Compilation successful!
echo [3/3] Running application...
echo.

java -cp src/main/java com.military.coordination.core.Main

echo.
echo Application finished.
pause
