# BookMyShow - Security & Coding Standards Guide

## Document Version: 2.0
**Last Updated**: March 2026  
**Status**: Implementation Complete

---

## Table of Contents
1. [Overview](#overview)
2. [Spring Boot Backend Security](#spring-boot-backend-security)
3. [Angular Frontend Security](#angular-frontend-security)
4. [Shared Security Practices](#shared-security-practices)
5. [Coding Standards](#coding-standards)
6. [Deployment Security](#deployment-security)
7. [Compliance Checklist](#compliance-checklist)

---

## Overview

This document outlines security best practices and coding standards for the BookMyShow application, following the latest guidelines from:
- **Spring Boot 3.2+** Security Framework
- **Angular 18 LTS** Best Practices
- **OWASP Top 10** Vulnerabilities Prevention
- **Java 17** Standards

---

## Spring Boot Backend Security

### 1. Authentication & Authorization

#### JWT Token-Based Authentication
```java
// Use JWT tokens for stateless authentication
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
```

**Best Practices:**
- ✅ Use JWT tokens with expiration (15-60 minutes)
- ✅ Implement refresh token mechanism
- ✅ Store tokens in httpOnly cookies (not localStorage)
- ✅ Include user roles and permissions in token
- ❌ Don't store sensitive data in JWT payload

#### Role-Based Access Control (RBAC)
```java
@PostMapping("/add")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> addMovie(@Valid @RequestBody MovieAddRequestBody req) {
    // Only ADMIN can add movies
}
```

**Roles:**
- `ADMIN` - Full system access, movie management
- `USER` - View movies, book tickets
- `GUEST` - Limited public access

### 2. Input Validation

#### Use Spring Validation Framework
```java
@NotBlank(message = "User ID cannot be empty")
@Size(min = 3, max = 50)
private String userId;

@Valid @RequestBody
public ResponseEntity<?> userAuth(@Valid @RequestBody UserAuthReq userInfo) {
    // Validation happens automatically
}
```

**Validation Rules:**
- Use `@NotNull`, `@NotEmpty`, `@NotBlank` for required fields
- Use `@Size`, `@Min`, `@Max` for length/value constraints
- Use `@Email`, `@Pattern` for format validation
- Always use `@Valid` on request body parameters

### 3. Password Security

#### BCrypt Password Hashing
```java
@Bean
public PasswordEncoder passwordEncoder() {
    // Strength 12 provides good security vs performance
    return new BCryptPasswordEncoder(12);
}
```

**Implementation:**
- Hash passwords with BCrypt (strength 12+)
- Never store plain-text passwords
- Use salt (automatic with BCrypt)
- Implement password complexity requirements
- Require minimum 8 characters with mixed case, numbers, symbols

### 4. CORS Configuration

#### Strict CORS Policy
```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(Arrays.asList(
        "https://bookmyshow.com",      // Production
        "http://localhost:4200"         // Development
    ));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    config.setAllowCredentials(false);  // Use JWT instead
    config.setMaxAge(3600L);
    return source;
}
```

**Best Practices:**
- ✅ Whitelist specific origins only
- ✅ Don't use `*` wildcard in production
- ✅ Configure via environment variables
- ✅ Set `allowCredentials` to false with JWT tokens
- ✅ Use HTTPS in production

### 5. Security Headers

#### Implement HTTP Security Headers
```java
.headers(headers -> headers
    .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'"))
    .xssProtection()
    .frameOptions().deny()
    .httpStrictTransportSecurity()
)
```

**Required Headers:**
- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `X-XSS-Protection: 1; mode=block`
- `Strict-Transport-Security: max-age=31536000`
- `Content-Security-Policy: default-src 'self'`

### 6. Error Handling

#### Don't Expose Sensitive Information
```java
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    logger.error("Error details", ex);  // Log internally
    // Return generic message to client
    return new ResponseEntity<>(
        "An error occurred. Please try again later.",
        HttpStatus.INTERNAL_SERVER_ERROR
    );
}
```

**Guidelines:**
- Log error details internally
- Return generic messages to clients
- Never expose database information
- Never expose stack traces in production
- Include request ID for tracking

### 7. Database Security

#### SQL Injection Prevention
```java
// ✅ Use parameterized queries (JPA)
@Query("SELECT m FROM Movies m WHERE m.id = ?1")
Optional<Movies> findById(Long id);

// ❌ Never use string concatenation
String query = "SELECT * FROM movies WHERE id = " + id;
```

**Best Practices:**
- Use JPA/Hibernate for ORM
- Use parameterized queries always
- Never concatenate user input
- Validate and sanitize all inputs
- Use prepared statements

### 8. Logging & Monitoring

#### Secure Logging Practices
```java
logger.info("User authentication attempt for user: {}", sanitizeUserId(userId));
logger.error("Database error occurred", ex);  // Log exception

// ❌ Never log passwords or sensitive data
logger.info("User password: " + password);  // WRONG!

// ✅ Sanitize logs
private String sanitizeUserId(String userId) {
    return userId.substring(0, 3) + "***";
}
```

**Best Practices:**
- Never log passwords, tokens, or PII
- Log all security events (login, access, errors)
- Use structured logging (JSON format)
- Monitor for suspicious patterns
- Retain logs for 90+ days

---

## Angular Frontend Security

### 1. Authentication & Token Management

#### JWT Token Handling
```typescript
// Environment configuration
export const environment = {
  security: {
    jwtTokenKey: 'authToken',
    userInfoKey: 'userInfo'
  }
};

// Secure storage
public storeAuthToken(token: string): void {
    localStorage.setItem(environment.security.jwtTokenKey, token);
}

public getAuthToken(): string | null {
    return localStorage.getItem(environment.security.jwtTokenKey);
}
```

**Important Notes:**
- localStorage is vulnerable to XSS - use httpOnly cookies in production
- Implement automatic token refresh
- Clear tokens on logout
- Validate token expiration

### 2. HTTP Interceptor Security

#### Security Headers & Authorization
```typescript
@Injectable()
export class SecurityInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler) {
    let secureRequest = request.clone({
      setHeaders: {
        'Authorization': `Bearer ${token}`,
        'X-Requested-With': 'XMLHttpRequest',
        'X-CSRF-Token': csrfToken
      }
    });
    
    return next.handle(secureRequest).pipe(
      timeout(30000),  // 30 second timeout
      catchError(error => this.handleError(error))
    );
  }
}
```

**Best Practices:**
- Add Authorization header with Bearer token
- Implement request timeout
- Handle 401/403 errors with logout
- Add CSRF token to all state-changing requests
- Log all errors securely

### 3. XSS Prevention

#### Input Sanitization
```typescript
// ✅ Use Angular's built-in sanitization
constructor(private sanitizer: DomSanitizer) {}

sanitizeData(data: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(data);
}

// ✅ Use interpolation (safe by default)
<h1>{{ userName }}</h1>

// ❌ Don't use innerHTML directly
<div [innerHTML]="unsanitized"></div>  // UNSAFE!
```

**Best Practices:**
- Use Angular's interpolation `{{ }}`
- Use `DomSanitizer` for HTML content
- Never use `innerHTML` with user data
- Use `@HostBinding()` carefully
- Content Security Policy (CSP) headers

### 4. Form Validation

#### Angular Form Validation
```typescript
loginForm = new FormGroup({
  username: new FormControl('', [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(50)
  ]),
  password: new FormControl('', [
    Validators.required,
    Validators.minLength(8),
    this.passwordStrengthValidator()
  ])
});

// Custom validators
private passwordStrengthValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password = control.value;
    const isStrong = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/.test(password);
    return isStrong ? null : { passwordStrength: true };
  };
}
```

**Validation Rules:**
- Require minimum 8 characters
- Mix of uppercase, lowercase, numbers, symbols
- Maximum field lengths to prevent DoS
- Email format validation
- Custom validators for business logic

### 5. Content Security Policy

#### HTTP Header Configuration
```html
<!-- In index.html -->
<meta http-equiv="Content-Security-Policy" 
      content="default-src 'self'; 
               script-src 'self' 'unsafe-inline';
               style-src 'self' 'unsafe-inline';">
```

**Directives:**
- `default-src 'self'` - Only allow same-origin
- `script-src` - Controls JavaScript sources
- `style-src` - Controls CSS sources
- `img-src` - Controls image sources
- `font-src` - Controls font sources

### 6. HTTP Only Headers

#### Best Practices for Angular
```typescript
// ✅ Configure headers properly
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

@NgModule({
  imports: [HttpClientModule],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: SecurityInterceptor, multi: true }
  ]
})
export class AppModule { }

// ✅ Send credentials securely
const headers = new HttpHeaders({
  'Content-Type': 'application/json',
  'Authorization': `Bearer ${token}`
});

this.http.post(url, data, { 
  headers,
  withCredentials: false  // Don't send cookies with JWT
});
```

---

## Shared Security Practices

### 1. OWASP Top 10 Prevention

| Vulnerability | Prevention |
|---|---|
| **SQL Injection** | Use parameterized queries, ORM frameworks |
| **Broken Authentication** | JWT tokens, strong password hashing |
| **Sensitive Data Exposure** | HTTPS, encryption at rest, no logging |
| **XML External Entities (XXE)** | Disable XML processing, validate input |
| **Broken Access Control** | RBAC, @PreAuthorize, role checks |
| **Security Misconfiguration** | Security headers, environment config |
| **XSS** | Input sanitization, CSP headers |
| **Insecure Deserialization** | Validate serialized objects, use allowlists |
| **Using Components with Known Vulnerabilities** | Keep dependencies updated |
| **Insufficient Logging & Monitoring** | Log security events, monitor patterns |

### 2. Dependency Management

#### Keep Dependencies Updated
```bash
# Check for vulnerabilities
npm audit
mvn dependency:check

# Update dependencies regularly
npm update
mvn versions:display-dependency-updates
```

**Current Versions:**
- Angular: 18.x (LTS)
- TypeScript: 5.4.x
- Spring Boot: 3.2.x (LTS)
- Java: 17 LTS
- JWT: 0.12.3

### 3. Environment & Configuration

#### Externalize Configuration
```properties
# application.properties
cors.allowed-origins=https://bookmyshow.com,https://www.bookmyshow.com
jwt.secret=${JWT_SECRET}
jwt.expiration=900000
server.ssl.enabled=true

# Never commit secrets!
```

**Best Practices:**
- Use environment variables for secrets
- Use different configs for dev/prod
- Don't commit `.env` files
- Rotate secrets regularly
- Use secret management tools (Vault, AWS Secrets Manager)

### 4. Rate Limiting

#### Implement Rate Limiting
```java
// Use Spring Cloud Resilience4j
@CircuitBreaker(name = "userService", fallbackMethod = "fallback")
@Retry(name = "userService")
public ResponseEntity<?> authenticateUser(UserAuthReq request) {
    // Implementation
}
```

**Rules:**
- 5 login attempts per 15 minutes
- 100 API calls per minute per user
- Progressive delays on repeated failures
- Temporary account lock after threshold

### 5. API Security

#### Versioning & Documentation
```java
// API versioning
@RequestMapping("/api/v1/user")
@RestController
public class UserController {
    // v1 endpoints
}

// Swagger/OpenAPI documentation
@Configuration
@EnableOpenApi
public class SwaggerConfig { }
```

**Best Practices:**
- Version your APIs
- Document all endpoints
- Use meaningful error codes
- Implement pagination for list endpoints
- Deprecate gracefully

---

## Coding Standards

### 1. Naming Conventions

#### Java
```java
// Classes - PascalCase
public class UserService { }
public class SecurityConfig { }

// Methods - camelCase
public void authenticateUser() { }
public boolean isTokenValid() { }

// Constants - UPPER_SNAKE_CASE
private static final String JWT_SECRET = "secret";
private static final int MAX_LOGIN_ATTEMPTS = 5;

// Variables - camelCase
private String userEmail;
private List<Movies> movieList;
```

#### TypeScript/Angular
```typescript
// Classes - PascalCase
export class UserService { }
export class SignInDialogComponent { }

// Interfaces - PascalCase with "I" prefix
export interface IUserDetails { }
export interface IAuthResponse { }

// Methods - camelCase
public authenticateUser() { }
public isTokenValid() { }

// Constants - UPPER_SNAKE_CASE
private readonly MAX_RETRIES = 3;
private readonly TOKEN_KEY = 'authToken';

// Variables - camelCase
private userName: string;
private movieList: Movie[];
```

### 2. Code Organization

#### Package Structure (Java)
```
com.bookmyshow
├── config/           # Configuration classes
├── controller/       # REST controllers
├── service/          # Business logic
├── repository/       # Data access
├── model/            # Entity models
├── exception/        # Custom exceptions
├── security/         # Security utilities
└── util/             # Utility classes
```

#### Module Structure (Angular)
```
src/app/
├── core/             # Services, guards, interceptors
├── shared/           # Shared components, models
├── features/         # Feature modules
├── interceptors/     # HTTP interceptors
├── models/           # Data models
└── environment/      # Configuration
```

### 3. JavaDoc & Comments

#### Document Public APIs
```java
/**
 * Authenticate user with credentials.
 * 
 * @param userId User identifier (3-50 chars)
 * @param userPassword User password (must meet complexity requirements)
 * @return ResponseEntity with user info and JWT token
 * @throws IllegalArgumentException if validation fails
 * @throws SecurityException if authentication fails
 * 
 * @see UserAuthReq for request structure
 * @see UserDetails for response structure
 */
public ResponseEntity<?> authenticateUser(String userId, String userPassword) {
    // Implementation
}
```

### 4. Unit Testing

#### Test Coverage Requirements
```java
// Minimum 80% code coverage
// Test all public methods
// Test error conditions

@Test
public void testAuthenticationSuccess() {
    // Arrange
    UserAuthReq request = new UserAuthReq("user123", "Password123!");
    
    // Act
    ResponseEntity<?> response = controller.userAuth(request);
    
    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
}

@Test
public void testAuthenticationFailure() {
    // Test invalid credentials
}

@Test
public void testInputValidation() {
    // Test validation annotations
}
```

### 5. Error Handling

#### Use Standard Exceptions
```java
// ✅ Use specific exceptions
throw new ResourceNotFoundException("Movie not found");
throw new UnauthorizedException("Invalid credentials");

// ❌ Don't use generic Exception
throw new Exception("Error");

// Create custom exception classes
public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) {
        super(message);
    }
}
```

### 6. Code Quality Tools

#### SonarQube Configuration
```properties
# sonar-project.properties
sonar.projectKey=bookmyshow
sonar.sources=src/main
sonar.tests=src/test
sonar.coverage.exclusions=**/*Config.java
sonar.java.codeCoveragePlugin=jacoco
```

#### SpotBugs/Checkstyle
```xml
<!-- checkstyle.xml -->
<module name="Checker">
    <module name="JavadocPackage"/>
    <module name="LineLength">
        <property name="max" value="120"/>
    </module>
</module>
```

---

## Deployment Security

### 1. Development Environment (Dev)

```properties
# application-dev.properties
server.ssl.enabled=false
logging.level.root=INFO
security.enable-debug=true
cors.allowed-origins=http://localhost:4200
```

### 2. Production Environment (Prod)

```properties
# application-prod.properties
server.ssl.enabled=true
server.ssl.key-store=${SSL_KEYSTORE_PATH}
server.ssl.key-store-password=${SSL_PASSWORD}
logging.level.root=WARN
logging.level.security=INFO
security.enable-debug=false
cors.allowed-origins=https://bookmyshow.com

# No database credentials in code!
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
```

### 3. HTTPS/TLS Configuration

```yaml
# application.yml
server:
  ssl:
    enabled: true
    key-store: ${KEYSTORE_PATH}
    key-store-password: ${KEYSTORE_PASSWORD}
    key-store-type: PKCS12
    key-alias: tomcat
    protocol: TLSv1.3
    enabled-protocols: TLSv1.3,TLSv1.2
```

### 4. CI/CD Security

#### GitHub Actions Example
```yaml
name: Security Checks
on: [push, pull_request]

jobs:
  security:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      # SAST Analysis
      - name: SonarQube Analysis
        run: mvn sonar:sonar -Dsonar.login=${{ secrets.SONAR_TOKEN }}
      
      # Dependency Check
      - name: OWASP Dependency Check
        run: |
          npm audit
          mvn dependency:check
      
      # Build & Test
      - name: Build and Test
        run: |
          mvn clean test
          npm run build
```

---

## Compliance Checklist

### Pre-Deployment Security Review

- [ ] **Authentication**
  - [ ] JWT tokens implemented
  - [ ] Password hashed with BCrypt (strength 12+)
  - [ ] Token expiration configured
  - [ ] Refresh token mechanism implemented

- [ ] **Authorization**
  - [ ] Role-based access control (RBAC) implemented
  - [ ] @PreAuthorize annotations on protected endpoints
  - [ ] Admin endpoints protected
  - [ ] User endpoints with role validation

- [ ] **Input Validation**
  - [ ] All requests validated
  - [ ] @Valid annotations on controllers
  - [ ] Size/format constraints enforced
  - [ ] SQL injection prevented (parameterized queries)
  - [ ] XSS prevention (input sanitization)

- [ ] **Data Security**
  - [ ] HTTPS/TLS enabled
  - [ ] Sensitive data not logged
  - [ ] Database encryption enabled
  - [ ] Secrets in environment variables
  - [ ] No hardcoded credentials

- [ ] **CORS/Headers**
  - [ ] CORS configured with whitelisted origins
  - [ ] Security headers implemented
  - [ ] CSP headers configured
  - [ ] HSTS enabled in production

- [ ] **Error Handling**
  - [ ] Generic error messages to clients
  - [ ] Detailed errors logged internally
  - [ ] Stack traces not exposed
  - [ ] Custom exception handlers

- [ ] **Dependencies**
  - [ ] All dependencies up to date
  - [ ] No known vulnerabilities
  - [ ] Spring Boot 3.2+ (LTS)
  - [ ] Angular 18+ (LTS)
  - [ ] Java 17+

- [ ] **Logging & Monitoring**
  - [ ] Security events logged
  - [ ] Audit trail maintained
  - [ ] Monitoring alerts configured
  - [ ] Log retention policy (90+ days)

- [ ] **Testing**
  - [ ] Unit tests written (80%+ coverage)
  - [ ] Security tests implemented
  - [ ] Penetration testing done
  - [ ] Integration tests passing

- [ ] **Documentation**
  - [ ] API documentation complete
  - [ ] Security guide updated
  - [ ] Deployment guide created
  - [ ] Incident response plan defined

---

## Security Incident Response

### Reporting Security Issues

**DO NOT** publicly disclose security vulnerabilities.

**Email**: security@bookmyshow.com  
**Include:**
- Description of vulnerability
- Steps to reproduce
- Potential impact
- Suggested fix (if any)

### Response Timeline
- **24 hours**: Acknowledgment
- **7 days**: Initial assessment
- **30 days**: Fix or mitigation
- **90 days**: Public disclosure (after fix deployed)

---

## Resources

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Angular Security Guide](https://angular.io/guide/security)
- [CWE Top 25](https://cwe.mitre.org/top25/)
- [NIST Cybersecurity Framework](https://www.nist.gov/cyberframework)

---

## Version History

| Version | Date | Changes |
|---------|------|---------|
| 2.0 | March 2026 | Complete security overhaul, Spring Boot 3.2+, Angular 18 LTS |
| 1.0 | 2024 | Initial version |

---

**Document Owner**: BookMyShow Security Team  
**Last Review Date**: March 2026  
**Next Review Date**: June 2026
