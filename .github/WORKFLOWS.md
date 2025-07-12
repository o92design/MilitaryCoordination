# GitHub Actions Workflows

This document describes all the automated workflows configured for the Military Coordination project. These workflows provide comprehensive CI/CD, security, quality assurance, and maintenance automation.

## 🔄 Workflow Overview

| Workflow | Purpose | Trigger | Duration |
|----------|---------|---------|----------|
| [CI](#-ci-workflow) | Continuous Integration | Push, PR | ~5-10 min |
| [Security](#-security-workflow) | Security scanning | Daily, PR | ~10-15 min |
| [Release](#-release-workflow) | Automated releases | Tag push | ~15-20 min |
| [Performance](#-performance-monitoring) | Performance tracking | Push to main | ~10-15 min |
| [Dependencies](#-dependency-updates) | Dependency updates | Weekly | ~5-10 min |
| [Documentation](#-documentation) | Docs generation | Push, PR | ~5-10 min |

## 🚀 CI Workflow

**File:** `.github/workflows/ci.yml`

### Purpose
Continuous Integration pipeline that validates code changes, runs tests, and builds artifacts.

### Triggers
- Push to `main` or `develop` branches
- Pull requests to `main`
- Manual dispatch

### Jobs

#### Code Quality & Testing
- **Java Version Matrix**: Tests on Java 21 and 22
- **Code Formatting**: Validates Checkstyle compliance
- **Static Analysis**: Runs SpotBugs for bug detection
- **Unit Testing**: Executes JUnit tests with coverage
- **Integration Testing**: Validates component interactions
- **Performance Testing**: Benchmarks critical operations

#### Build & Artifacts
- **Maven Build**: Compiles and packages application
- **Dependency Analysis**: Checks for vulnerabilities
- **Artifact Upload**: Saves JARs and reports

### Quality Gates
- ✅ All tests must pass
- ✅ Code coverage > 80%
- ✅ No critical SpotBugs violations
- ✅ Checkstyle compliance
- ✅ No high/critical security vulnerabilities

### Outputs
- JAR artifacts
- Test reports
- Coverage reports
- Code quality reports

## 🔒 Security Workflow

**File:** `.github/workflows/security.yml`

### Purpose
Automated security scanning and vulnerability management.

### Triggers
- Daily at 2 AM UTC
- Pull requests (security-relevant changes)
- Manual dispatch

### Security Checks

#### Dependency Scanning
- **OWASP Dependency Check**: Identifies known vulnerabilities
- **License Compliance**: Validates dependency licenses
- **Supply Chain Security**: Checks for malicious packages

#### Code Analysis
- **CodeQL Analysis**: Static application security testing
- **Secret Scanning**: Detects accidentally committed secrets
- **Vulnerability Database**: Updates CVE information

#### Automated Response
- **Issue Creation**: Auto-creates security issues for vulnerabilities
- **Severity Classification**: High/Medium/Low priority assignment
- **Notification**: Alerts maintainers of critical issues

### Security Reports
- Vulnerability assessment
- License compliance report
- Security recommendations

## 🏷️ Release Workflow

**File:** `.github/workflows/release.yml`

### Purpose
Automated release process with validation and deployment.

### Triggers
- Git tag push (format: `v*.*.*`)
- Manual dispatch

### Release Process

#### Pre-Release Validation
- **Version Validation**: Ensures semantic versioning
- **Build Verification**: Full clean build and test
- **Quality Checks**: All CI quality gates must pass
- **Change Detection**: Validates meaningful changes since last release

#### Release Creation
- **GitHub Release**: Creates release with auto-generated notes
- **Artifact Upload**: Attaches JAR files and documentation
- **Tag Management**: Ensures proper tag creation and linking

#### Post-Release
- **Documentation Deployment**: Updates GitHub Pages
- **Notification**: Announces release to stakeholders
- **Version Bump**: Prepares for next development cycle

### Release Artifacts
- Application JAR
- Source code archive
- JavaDoc documentation
- Release notes

## 📊 Performance Monitoring

**File:** `.github/workflows/performance.yml`

### Purpose
Continuous performance monitoring and regression detection.

### Triggers
- Push to `main` branch
- Weekly scheduled runs
- Manual dispatch

### Performance Testing

#### Benchmarks
- **Game Loop Performance**: Frame rate and latency testing
- **Data Structure Operations**: Collection performance
- **Algorithm Efficiency**: Core algorithm benchmarking
- **Memory Usage**: Heap and garbage collection analysis

#### Regression Detection
- **Historical Comparison**: Compares against previous runs
- **Threshold Monitoring**: Alerts on significant degradation
- **Trend Analysis**: Identifies gradual performance decline

#### Reporting
- **Performance Dashboard**: Visual performance trends
- **Regression Alerts**: Automated issue creation for regressions
- **Optimization Recommendations**: Suggests performance improvements

### Performance Metrics
- Frame rate (target: 60 FPS)
- Operation latency (target: <16ms)
- Memory usage patterns
- CPU utilization

## 🔄 Dependency Updates

**File:** `.github/workflows/dependency-updates.yml`

### Purpose
Automated dependency management and security updates.

### Triggers
- Weekly (Mondays at 8 AM UTC)
- Manual dispatch

### Update Types

#### Regular Updates
- **Minor/Patch Updates**: Safe automatic updates
- **Testing**: Validates compatibility with new versions
- **Pull Request Creation**: Automated PR with change summary

#### Security Updates
- **Vulnerability Detection**: Identifies security-related updates
- **Emergency Updates**: Applies critical security patches
- **Priority Handling**: Fast-tracks security PRs

#### Java Version Updates
- **Version Compatibility**: Tests with newer Java versions
- **Migration Planning**: Creates issues for major version updates
- **Feature Adoption**: Suggests new language features

### Update Process
1. **Scan**: Check for available updates
2. **Filter**: Categorize by safety and importance
3. **Test**: Validate compatibility
4. **Create PR**: Automated pull request
5. **Review**: Manual review and approval

## 📚 Documentation

**File:** `.github/workflows/documentation.yml`

### Purpose
Automated documentation generation and quality assurance.

### Triggers
- Push to `main` or `develop`
- Pull requests
- Changes to documentation files

### Documentation Tasks

#### JavaDoc Generation
- **API Documentation**: Generates comprehensive JavaDoc
- **Coverage Check**: Validates JavaDoc completeness
- **Quality Assessment**: Reports missing documentation

#### Markdown Validation
- **Link Checking**: Validates all markdown links
- **Linting**: Ensures consistent markdown formatting
- **Index Generation**: Creates documentation navigation

#### README Quality
- **Completeness Check**: Validates README sections
- **Improvement Suggestions**: Identifies missing content
- **Quality Scoring**: Provides objective README assessment

#### Documentation Deployment
- **GitHub Pages**: Deploys documentation website
- **Version Management**: Maintains documentation versions
- **Search Integration**: Enables documentation search

### Documentation Outputs
- GitHub Pages site
- JavaDoc API reference
- Markdown documentation
- Quality reports

## ⚙️ Workflow Configuration

### Environment Variables
```yaml
JAVA_VERSION: 21          # Primary Java version
MAVEN_OPTS: "-Xmx2g"     # Maven memory settings
NODE_VERSION: 20         # Node.js for tooling
```

### Secrets Required
- `GITHUB_TOKEN`: Repository access (auto-provided)
- `CODECOV_TOKEN`: Code coverage reporting (optional)

### Artifact Retention
- **Build Artifacts**: 30 days
- **Test Reports**: 90 days
- **Security Reports**: 365 days
- **Performance Data**: 180 days

## 🔧 Customization

### Adding New Workflows
1. Create `.github/workflows/new-workflow.yml`
2. Define triggers and jobs
3. Add appropriate quality gates
4. Update this documentation

### Modifying Existing Workflows
1. Test changes on feature branch
2. Validate with workflow dispatch
3. Update documentation
4. Deploy to main branch

### Quality Gate Adjustments
- **Code Coverage**: Modify in `ci.yml` (currently 80%)
- **Performance Thresholds**: Update in `performance.yml`
- **Security Severity**: Adjust in `security.yml`

## 📋 Monitoring & Maintenance

### Workflow Health
- **Success Rate**: Monitor workflow success/failure rates
- **Duration Trends**: Track workflow execution times
- **Resource Usage**: Monitor runner usage and costs

### Regular Reviews
- **Monthly**: Review workflow performance and adjust
- **Quarterly**: Update tool versions and configurations
- **Annually**: Major workflow architecture review

### Troubleshooting
- **Failed Workflows**: Check logs and error messages
- **Performance Issues**: Review resource allocation
- **Security Alerts**: Immediate investigation required

## 🚀 Benefits

### Development Velocity
- **Automated Testing**: Catches issues early
- **Fast Feedback**: Quick CI/CD cycles
- **Quality Assurance**: Consistent code quality

### Security Posture
- **Vulnerability Detection**: Proactive security scanning
- **Compliance**: Automated license checking
- **Response**: Quick security patch deployment

### Project Health
- **Documentation**: Always up-to-date docs
- **Dependencies**: Secure and current libraries
- **Performance**: Continuous monitoring and optimization

### Team Productivity
- **Automation**: Reduces manual tasks
- **Consistency**: Standardized processes
- **Visibility**: Clear project status and metrics

---

*This documentation is automatically updated with workflow changes. Last updated: Generated by documentation workflow.*
