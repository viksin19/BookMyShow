# BookMyShow - Quick Security Reference Guide

**For Developers | March 2026**

---

## ⚡ Critical Security Remember

### 🔴 DON'T
- ❌ Store passwords in plain text
- ❌ Log sensitive data (passwords, tokens, PII)
- ❌ Use `localhost` in production CORS
- ❌ Trust user input - always validate
- ❌ Expose stack traces to users
- ❌ Commit secrets/credentials to Git
- ❌ Use deprecated Spring Security APIs
- ❌ Make HTTP calls without HTTPS in production

### ✅ DO
- ✅ Hash passwords with BCrypt (strength 12+)
- ✅ Validate all inputs (@Valid annotations)
- ✅ Use environment variables for configuration
- ✅ Return generic error messages to clients
- ✅ Log security events securely
- ✅ Use JWT tokens for authentication
- ✅ Add @PreAuthorize on protected endpoints
- ✅ Always use HTTPS in production

---

## 🔐 Common Security Tasks

### Add Protected Endpoint (Backend)

```java
@PostMapping("/admin/action")
@PreAuthorize("hasRole('ADMIN')")  // <- Add this
public ResponseEntity<?> protectedAction(@Valid @RequestBody Request req) {
    logger.info("Admin action performed");
    return new ResponseEntity<>("Success", HttpStatus.OK);
}
```

### Validate User Input (Backend)

```java
public class UserAuthReq {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(min = 5, max = 100)
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be 8-100 chars")
    private String password;
}
```

### Use Secure API Call (Frontend)

```typescript
// UserService already handles this!
this.userService.authenticateUser(email, password)
    .subscribe({
        next: (response) => {
            // Token auto-stored securely
            this.router.navigate(['/dashboard']);
        },
        error: (error) => {
            // Already logged and handled
            this.showError(error.message);
        }
    });
```

### Add Authorization Header (Frontend)

```typescript
// SecurityInterceptor handles this automatically!
// Just make HTTP calls normally:
this.http.get('/api/movie/tabledata')
    .subscribe(data => {
        // Authorization header auto-added by interceptor
    });
```

---

## 🚀 Development Workflow

### Run Development Server

```bash
# Backend
cd EzeTapBackend/bookmyshow
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Frontend
cd BookMyShowFrontEnd
npm install
npm start
```

### Build Production

```bash
# Backend
mvn clean package -DskipTests -P prod

# Frontend
ng build --configuration production
```

### Run Security Checks

```bash
# Check for vulnerabilities
npm audit
mvn dependency:check

# Run tests
npm run test
mvn test

# Lint code
npm run lint
mvn spotbugs:check
```

---

## 📋 Configuration Quick Reference

### Development Environment

**Backend** (`application-dev.properties`):
- No HTTPS
- DEBUG logging
- Relaxed CORS
- DevTools enabled

**Frontend** (`environment.ts`):
- `apiBaseUrl: 'http://localhost:8000/api'`
- Debug mode enabled
- All logging enabled

### Production Environment

**Backend** (`application-prod.properties`):
- HTTPS enabled
- WARN logging
- Strict CORS
- All secrets from env vars

**Frontend** (`environment.prod.ts`):
- `apiBaseUrl: 'https://api.bookmyshow.com/api'`
- Debug mode disabled
- Logging disabled

---

## 🔑 Environment Variables Required

### Backend (Production)

```bash
JWT_SECRET=your-very-strong-secret-key-32-chars-minimum
CORS_ORIGINS=https://bookmyshow.com,https://www.bookmyshow.com
DB_URL=jdbc:mysql://prod-db:3306/bookmyshow
DB_USERNAME=secure-username
DB_PASSWORD=secure-password
SSL_KEYSTORE_PATH=/path/to/keystore.p12
SSL_KEYSTORE_PASSWORD=keystore-password
```

### Frontend (Production)

```bash
# In .env file (not committed to Git)
API_URL=https://api.bookmyshow.com/api
```

---

## ✔️ Pre-Commit Checklist

Before pushing code:

```
☐ No passwords or tokens logged
☐ Input validation added (@Valid, @NotNull)
☐ Authorization check added (@PreAuthorize)
☐ Error handling added (try-catch)
☐ No hardcoded localhost URLs
☐ No console.log() debug statements (frontend)
☐ No secrets committed
☐ Tests written (>80% coverage)
☐ Code builds without warnings
☐ No security warnings in audit
```

---

## 🐛 Common Mistakes & Fix

### Mistake 1: Logging Password
```java
// ❌ WRONG
logger.info("User password: {}", password);

// ✅ CORRECT
logger.info("Login attempt for user");
logger.warn("Failed login attempt");
```

### Mistake 2: Missing Validation
```java
// ❌ WRONG
@PostMapping("/add")
public ResponseEntity<?> addUser(@RequestBody User user) { }

// ✅ CORRECT
@PostMapping("/add")
public ResponseEntity<?> addUser(@Valid @RequestBody User user) { }
```

### Mistake 3: No Authorization
```java
// ❌ WRONG
@PostMapping("/admin/delete")
public ResponseEntity<?> deleteUser(@PathVariable Long id) { }

// ✅ CORRECT
@PostMapping("/admin/delete")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> deleteUser(@PathVariable Long id) { }
```

### Mistake 4: Direct localStorage (Frontend)
```typescript
// ❌ WRONG
localStorage.setItem('token', response.token);

// ✅ CORRECT
this.userService.storeAuthToken(response.token);
```

### Mistake 5: Hardcoded URLs (Frontend)
```typescript
// ❌ WRONG
const url = 'http://localhost:8000/api/movie';

// ✅ CORRECT
const url = `${environment.apiBaseUrl}/movie`;
```

---

## 📞 Support & Resources

### Getting Help
- **Security Questions**: Check `SECURITY_AND_STANDARDS.md`
- **Implementation Details**: See `IMPROVEMENTS_SUMMARY.md`
- **Dependencies**: Check Spring Boot docs or Angular docs
- **Bugs**: Create GitHub issue with [SECURITY] tag

### Links
- [Spring Security Docs](https://spring.io/projects/spring-security)
- [Angular Security Guide](https://angular.io/guide/security)
- [OWASP Cheat Sheet](https://cheatsheetseries.owasp.org/)

### Contact
- **Dev Lead**: dev-lead@bookmyshow.com
- **Security Team**: security@bookmyshow.com
- **On-Call**: Check team calendar

---

## 🎓 Quick Learning Tips

### For Backend Developers
1. Always use `@Valid` on request bodies
2. Check Spring Security docs for role-based auth
3. Return generic error messages, log details
4. Use BCrypt for passwords, JWT for tokens
5. Test with curl: `curl -H "Authorization: Bearer TOKEN" http://localhost:8000/api/endpoint`

### For Frontend Developers
1. Use UserService for all auth operations
2. SecurityInterceptor adds headers automatically
3. Never store sensitive data in localStorage
4. Test with browser DevTools Network tab
5. Check Network tab for Authorization headers

### For All Developers
1. Read SECURITY_AND_STANDARDS.md
2. Follow naming conventions exactly
3. Test before pushing code
4. Use security tools (npm audit, mvn check)
5. Ask team before deviating from standards

---

## ✨ Profile-based Deployment

### Development
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
# or
java -jar app.jar --spring.profiles.active=dev
```

### Production
```bash
java -jar app.jar \
  --spring.profiles.active=prod \
  --JWT_SECRET=$JWT_SECRET \
  --CORS_ORIGINS=$CORS_ORIGINS
```

---

## 📊 Security Metrics & KPIs

Monitor these metrics:
- **Failed Login Attempts**: Alert if >10/hour
- **API Response Time**: Alert if >5 seconds
- **Error Rate**: Alert if >1%
- **Security Events**: Log all auth events
- **Dependency Vulnerabilities**: Check weekly

---

## 📅 Security Updates Schedule

- **Weekly**: Run `npm audit` and `mvn dependency:check`
- **Monthly**: Review security logs
- **Quarterly**: Penetration testing
- **Yearly**: Full security audit

---

**Version**: 2.0 | **Last Updated**: March 2026 | **Status**: ✅ Ready for Use
