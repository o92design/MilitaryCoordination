# Development Setup Guide

## ✅ Current Working Setup
- ✅ **Run Script**: Use `./run.bat` to compile and run
- ✅ **VS Code Tasks**: Build and run tasks configured
- ✅ **Launch Configs**: Debug configurations ready

## 🚀 Quick Start (Current)
1. **Run the application**:
   ```bash
   ./run.bat
   ```
   OR use VS Code Task: `Ctrl+Shift+P` → "Tasks: Run Task" → "Run Application (Batch)"

2. **VS Code Launch**: Press `F5` or use Run and Debug panel

## 📦 Setting Up Maven (Recommended)

### Windows Installation:
1. **Download Maven**: https://maven.apache.org/download.cgi
2. **Extract** to `C:\Program Files\Apache\maven`
3. **Add to PATH**:
   - Open System Properties → Advanced → Environment Variables
   - Add `C:\Program Files\Apache\maven\bin` to PATH
4. **Verify**: Open new terminal and run `mvn --version`

### Alternative - Using Chocolatey:
```bash
choco install maven
```

### Alternative - Using Scoop:
```bash
scoop install maven
```

## 🔧 Maven Commands (Once Installed)
```bash
# Build project
mvn clean compile

# Run application
mvn compile exec:java

# Run tests
mvn test

# Package JAR
mvn clean package

# Run JAR
java -jar target/military-coordination-system-1.0-SNAPSHOT.jar
```

## 📁 VS Code Features Ready
- ✅ **Tasks** (Ctrl+Shift+P → "Tasks: Run Task"):
  - Build Project
  - Run Application (Batch) - Default
  - Run Tests (needs Maven)
  - Clean Project (needs Maven)
  - Package JAR (needs Maven)

- ✅ **Launch Configurations** (F5 or Run and Debug):
  - Run Military Coordination System
  - Debug Military Coordination System

- ✅ **Java Extension Pack**: Should work with launch configs

## 🎯 Current Application Output
```
Military Coordination System Initialized.
Recon Unit: ReconUnit{id='Recon-Alpha', unitSize=10, intelReports=[]}
Recon Unit Two: ReconUnit{id='Recon-Beta', unitSize=15, intelReports=[]}
Signal Tower: SignalTower{id='Tower-1', signalStrength=100, reconUnits=2 units, intelReports=0 reports}
```

## 🔍 Troubleshooting
- **Java not found**: Ensure Java 21+ is installed and in PATH
- **Maven not found**: Follow Maven installation steps above
- **VS Code Java issues**: Install "Extension Pack for Java"
