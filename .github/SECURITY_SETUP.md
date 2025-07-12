# 🔐 Security Configuration Guide

## NVD API Key Setup (Recommended)

The OWASP Dependency Check plugin works much faster with an NVD (National Vulnerability Database) API key. Without it, dependency scans can take 20+ minutes instead of 2-3 minutes.

### Why You Need an NVD API Key

- **Speed**: Reduces scan time from 20+ minutes to 2-3 minutes
- **Reliability**: Avoids timeout issues and rate limiting
- **Completeness**: Gets the most up-to-date vulnerability data

### Getting an NVD API Key (FREE)

1. **Visit NIST NVD**: Go to [https://nvd.nist.gov/developers/request-an-api-key](https://nvd.nist.gov/developers/request-an-api-key)

2. **Request API Key**:
   - Fill out the simple form
   - Provide your email address
   - Accept the terms of use
   - Submit the request

3. **Receive Key**: You'll get your API key via email within minutes

### Setting Up the API Key in GitHub

1. **Go to Repository Settings**:
   - Navigate to your GitHub repository
   - Click on "Settings" tab
   - Go to "Secrets and variables" → "Actions"

2. **Add New Secret**:
   - Click "New repository secret"
   - Name: `NVD_API_KEY`
   - Value: Your API key from NIST
   - Click "Add secret"

### Workflow Behavior

#### With NVD API Key ✅
- **Full OWASP Dependency Check**: Complete vulnerability scanning
- **Fast Execution**: 2-3 minutes
- **Comprehensive Results**: Latest vulnerability database
- **High Accuracy**: Fewer false positives

#### Without NVD API Key ⚠️
- **Fast Mode**: Limited OWASP scanning with timeout protection
- **Alternative Scanning**: OSS Index (Sonatype) for basic vulnerability detection
- **GitHub Advisory Database**: Additional security checks
- **Quick Execution**: 5-10 minutes total

## Security Scanning Features

### OWASP Dependency Check
- **CVE Detection**: Identifies known vulnerabilities
- **CVSS Scoring**: Risk assessment (0-10 scale)
- **Fail Threshold**: CVSS 7+ fails the build
- **Report Generation**: Detailed HTML reports

### OSS Index (Backup Scanner)
- **Sonatype Database**: Community vulnerability data
- **Fast Scanning**: No API key required
- **JSON Reports**: Machine-readable results
- **Integration**: Seamless fallback when OWASP times out

### GitHub Advisory Database
- **Native Integration**: Built into GitHub Actions
- **Real-time Data**: Up-to-date security advisories
- **Package Specific**: Targeted vulnerability information

## Configuration Options

### Security Thresholds
```yaml
# Current settings in security.yml
-DfailBuildOnCVSS=7        # Fail on high/critical vulnerabilities
-DsuppressValidation=true   # Skip validation for faster scans
-DnvdValidForHours=24      # Cache vulnerability data for 24 hours
```

### Performance Optimization
```yaml
# Disabled analyzers for Java projects
-DretireJsAnalyzerEnabled=false    # Skip JavaScript analysis
-DnodeAnalyzerEnabled=false        # Skip Node.js analysis
-DassemblyAnalyzerEnabled=false    # Skip .NET assembly analysis
```

### Caching Strategy
- **Vulnerability Database**: Cached for 24 hours
- **Maven Dependencies**: Cached across builds
- **Analysis Results**: Cached per commit

## Troubleshooting

### Common Issues

#### "Very Long Time" Warning
```
Warning: An NVD API Key was not provided - it is highly recommended
to use an NVD API key as the update can take a VERY long time without an API Key
```
**Solution**: Add NVD API key to GitHub secrets as described above.

#### Timeout Errors
**Solution**: The workflow automatically switches to fast mode with 10-minute timeout.

#### Rate Limiting
**Solution**: NVD API key eliminates rate limiting issues.

### Monitoring Security Scans

#### Success Indicators
- ✅ Scan completes in under 10 minutes
- ✅ No high/critical vulnerabilities found
- ✅ Reports generated and uploaded

#### Failure Indicators
- ❌ CVSS 7+ vulnerabilities detected
- ❌ Scan timeout (>10 minutes in fast mode)
- ❌ Network connectivity issues

## Security Workflow Schedule

### Automated Scans
- **Daily**: Monday 6 AM UTC (weekly dependency check)
- **On Push**: When `pom.xml` changes
- **On PR**: Security-relevant changes
- **Manual**: On-demand via workflow dispatch

### Issue Creation
- **High Severity**: Auto-creates GitHub issues
- **Critical Vulnerabilities**: Immediate notification
- **Dependency Updates**: Automated security patches

## Best Practices

### Development
1. **Regular Scans**: Don't wait for scheduled scans
2. **Dependency Updates**: Keep dependencies current
3. **Security Reviews**: Review auto-created security issues
4. **Testing**: Validate security patches

### Team Workflow
1. **API Key Setup**: Each team member should request NVD API key
2. **Local Scanning**: Run security checks before pushing
3. **Issue Triage**: Assign security issues promptly
4. **Documentation**: Keep security configs updated

## Quick Setup Commands

```bash
# Set up NVD API key (after getting it from NIST)
gh secret set NVD_API_KEY --body "your-api-key-here"

# Run security scan locally
mvn org.owasp:dependency-check-maven:check

# Run alternative security check
mvn org.sonatype.ossindex.maven:ossindex-maven-plugin:audit

# Test the security workflow
gh workflow run security.yml
```

## Status Check

After setting up the NVD API key, your security workflow will:
- ✅ Complete in 2-3 minutes instead of 20+ minutes
- ✅ Provide comprehensive vulnerability scanning
- ✅ Generate detailed security reports
- ✅ Automatically create issues for findings

---

**Need help?** Check the [WORKFLOWS.md](WORKFLOWS.md) for detailed workflow documentation or create an issue using our security template.

*Security is not a destination, it's a journey. Keep your dependencies updated! 🔐*
