@echo off
echo Cleaning Military Coordination System build artifacts...

REM Clean parent target directory
if exist "target" (
    echo Removing parent target directory...
    rmdir /s /q target
    echo Parent target directory cleaned!
) else (
    echo No parent target directory found.
)

REM Clean core module target directory
if exist "core\target" (
    echo Removing core module target directory...
    rmdir /s /q core\target
    echo Core module target directory cleaned!
) else (
    echo No core module target directory found.
)

REM Clean desktop module target directory
if exist "desktop\target" (
    echo Removing desktop module target directory...
    rmdir /s /q desktop\target
    echo Desktop module target directory cleaned!
) else (
    echo No desktop module target directory found.
)

echo Removing any .class files from source directories...
for /r core\src %%i in (*.class) do (
    echo Removing: %%i
    del "%%i"
)
for /r desktop\src %%i in (*.class) do (
    echo Removing: %%i
    del "%%i"
)

echo Clean complete!
pause
