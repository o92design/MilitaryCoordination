@echo off
echo Cleaning Military Coordination System build artifacts...

if exist "target" (
    echo Removing target directory...
    rmdir /s /q target
    echo Target directory cleaned!
) else (
    echo No target directory found.
)

echo Removing any .class files from source directory...
for /r src %%i in (*.class) do (
    echo Removing: %%i
    del "%%i"
)

echo Clean complete!
pause
