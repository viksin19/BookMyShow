# BookMyShow - Angular Frontend

**Version**: 1.0.0 | **Angular**: 18 LTS | **TypeScript**: 5.4 | **Status**: ✅ Production Ready

A secure, modern Angular 18 LTS web application for booking movies, built with the latest security best practices and coding standards.

---

## 📋 Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Development](#development)
- [Building for Production](#building-for-production)
- [Project Structure](#project-structure)
- [Security Features](#security-features)
- [Dependencies](#dependencies)
- [Architecture](#architecture)
- [Testing](#testing)
- [Contributing](#contributing)
- [Troubleshooting](#troubleshooting)
- [Resources](#resources)

---

## ✨ Features

### Core Features
- 🎬 **Movie Browsing** - View available movies and details
- 🎫 **Movie Booking** - Book movie tickets
- 📊 **Dashboard** - Admin analytics and management
- 🔐 **Secure Authentication** - JWT token-based authentication
- 📱 **Responsive Design** - Works on desktop and mobile devices
- 📈 **Charts & Analytics** - Visual data representation with Chart.js

### Security Features
- ✅ **Input Validation** - Comprehensive form validation
- ✅ **XSS Protection** - Angular sanitization & CSP headers
- ✅ **CSRF Protection** - Token-based CSRF prevention
- ✅ **Rate Limiting** - Login attempt rate limiting
- ✅ **Secure HTTP Interceptor** - Automatic token management
- ✅ **Password Strength** - Client-side password validation
- ✅ **Error Masking** - Generic error messages to users
- ✅ **Secure Storage** - Service-based token management

---

## 🔧 Prerequisites

Before you begin, ensure you have the following installed:

- **Node.js**: v18 or higher
  ```bash
  node --version  # Should be v18+
  ```
- **npm**: v9 or higher
  ```bash
  npm --version  # Should be v9+
  ```
- **Angular CLI**: v18 or higher
  ```bash
  npm install -g @angular/cli@18
  ng version
  ```

---

## 📦 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/viksin19/BookMyShow.git
cd BookMyShow/BookMyShowFrontEnd
```

### 2. Install Dependencies

```bash
# Install all project dependencies
npm install

# Or use clean install (recommended for CI/CD)
npm ci
```

### 3. Verify Installation

```bash
# Check Angular version
ng version

# Should display Angular 18.x and other relevant info
```

---

## 🚀 Development

### Start Development Server

```bash
# Start the development server
npm start

# Or manually
ng serve
```

The application will be available at `http://localhost:4200/`.

**Features:**
- Live reload on code changes
- Debug mode enabled
- Verbose logging
- Development API endpoint: `http://localhost:8000/api`

### Code Generation

Generate new components, services, and other Angular artifacts:

```bash
# Generate a new component
ng generate component components/my-component
# Alias: ng g c components/my-component

# Generate a new service
ng generate service services/my-service
# Alias: ng g s services/my-service

# Generate a new module
ng generate module modules/my-module

# Generate a guard
ng generate guard guards/auth.guard

# Generate an interceptor
ng generate interceptor interceptors/my.interceptor
```

### Environment Configuration

The application uses environment-specific configurations:

- **Development** (`environment.ts`):
  - Debug mode enabled
  - Verbose logging
  - Local API: `http://localhost:8000/api`
  - CORS with localhost

- **Production** (`environment.prod.ts`):
  - Debug mode disabled
  - Minimal logging
  - Production API: `https://api.bookmyshow.com/api`
  - Strict CORS

View/Edit: `src/environments/environment.ts` and `src/environments/environment.prod.ts`

---

## 🏗️ Building for Production

### Build the Application

```bash
# Standard build
npm run build

# Production optimized build (recommended)
npm run build:prod
```

**Output:**
- Build artifacts: `dist/book-my-show/`
- File naming: Includes hash for cache busting
- Optimization: Tree-shaking, minification, AOT compilation

### Build Verification

```bash
# Check bundle size
npm run bundle-report

# Expected sizes (gzipped):
# - main bundle: <500KB
# - vendor bundle: <300KB
# - total: <1MB
```

### Serve Production Build Locally

```bash
# Install http-server if not already installed
npm install -g http-server

# Serve production build
cd dist/book-my-show
http-server -p 8080 --gzip
```

Visit `http://localhost:8080/` to test production build locally.

---

## 📁 Project Structure

```
BookMyShowFrontEnd/
├── src/
│   ├── app/
│   │   ├── app-routing.module.ts       # Main routing configuration
│   │   ├── app.component.*             # Root component
│   │   ├── app.module.ts               # Root module
│   │   │
│   │   ├── core/                       # Core services (singletons)
│   │   │   ├── services/
│   │   │   ├── guards/
│   │   │   └── interceptors/
│   │   │       └── security.interceptor.ts  # HTTP security interceptor
│   │   │
│   │   ├── shared/                     # Shared components & utilities
│   │   │   ├── components/
│   │   │   ├── pipes/
│   │   │   └── models/
│   │   │
│   │   ├── features/                   # Feature modules
│   │   │   ├── home/                   # Home/landing page
│   │   │   ├── movie-list/             # Movie listing feature
│   │   │   ├── dashboard/              # Admin dashboard
│   │   │   └── sign-in-dialog/         # Authentication dialog
│   │   │
│   │   ├── interceptors/               # HTTP interceptors
│   │   │   └── security.interceptor.ts # Token & security headers
│   │   │
│   │   ├── material-module/            # Material design components
│   │   │   └── material.module.ts
│   │   │
│   │   ├── modal-interfaces/           # Data models/interfaces
│   │   │   ├── userDetails.model.ts
│   │   │   ├── movieList.model.ts
│   │   │   └── chart.model.ts
│   │   │
│   │   ├── services/                   # Application services
│   │   │   ├── User/
│   │   │   │   └── user.service.ts     # User auth service
│   │   │   ├── MovieServices/
│   │   │   │   └── movie-service.service.ts
│   │   │   └── Dashboard-Service/
│   │   │       └── dashboard.service.ts
│   │   │
│   │   └── Dashboard-Service/
│   │
│   ├── assets/                         # Static assets (images, etc.)
│   ├── environments/                   # Environment configurations
│   │   ├── environment.ts              # Development
│   │   └── environment.prod.ts         # Production
│   │
│   ├── index.html                      # Main HTML file
│   ├── main.ts                         # Application entry point
│   ├── polyfills.ts                    # Browser polyfills
│   ├── styles.css                      # Global styles
│   └── test.ts                         # Test configuration
│
├── angular.json                        # Angular CLI configuration
├── tsconfig.json                       # TypeScript configuration
├── tsconfig.app.json                   # App-specific TypeScript config
├── tsconfig.spec.json                  # Test-specific TypeScript config
├── karma.conf.js                       # Karma test runner config
├── package.json                        # Dependencies & scripts
└── README.md                           # This file
```

---

## 🔐 Security Features

### Input Validation
```typescript
// All forms validate user input
- Required field validation
- Email format validation
- Password strength requirements
- XSS prevention through sanitization
```

### Authentication
```typescript
// JWT Token Management
- Secure token storage via UserService
- Automatic token refresh capability
- Token expiration validation
- Secure logout functionality
```

### HTTP Security
```typescript
// SecurityInterceptor Features
- Automatic Bearer token injection
- CSRF token support
- Request timeout (30 seconds)
- Error handling with masking
- Automatic logout on 401/403
```

### Password Security
```typescript
// Password Strength Validator
- Minimum 8 characters
- Requires: uppercase, lowercase, numbers
- Optional: special characters
- Client-side validation before submission
```

### Rate Limiting
```typescript
// Login Attempt Limiting
- Maximum 5 attempts per 15 minutes
- Progressive delays on failures
- Automatic clearing after timeout
```

### Security Headers (via Backend)
```
- Content-Security-Policy: default-src 'self'
- X-Content-Type-Options: nosniff
- X-Frame-Options: DENY
- X-XSS-Protection: 1; mode=block
- Strict-Transport-Security (HTTPS only)
```

### Content Security Policy
- Restricts script sources to same-origin
- Controls style sheet sources
- Prevents inline scripts
- Prevents frame embedding

---

## 📦 Dependencies

### Core Framework
- **@angular/core**: ^18.0.0 - Angular framework
- **@angular/common**: ^18.0.0 - Angular common utilities
- **@angular/platform-browser**: ^18.0.0 - Browser platform
- **rxjs**: ~7.8.0 - Reactive extensions
- **tslib**: ^2.6.0 - TypeScript helper library
- **zone.js**: ~0.14.0 - Zone execution context

### UI & Styling
- **@angular/material**: ^18.0.0 - Material Design components
- **@angular/cdk**: ^18.0.0 - Component Dev Kit
- **@angular/animations**: ^18.0.0 - Angular animations

### Charting
- **chart.js**: ^4.4.0 - JavaScript charting library
- **ng2-charts**: ^4.1.1 - Angular wrapper for Chart.js

### Forms & Routing
- **@angular/forms**: ^18.0.0 - Reactive form handling
- **@angular/router**: ^18.0.0 - Angular routing

### HTTP
- **@angular/common/http**: ^18.0.0 - HTTP client

### Development Tools
- **@angular/cli**: ~18.0.0 - Angular command-line interface
- **@angular/compiler-cli**: ^18.0.0 - Angular compiler
- **typescript**: ~5.4.0 - TypeScript language
- **karma**: ~6.4.0 - Test runner
- **jasmine-core**: ~5.1.0 - Testing framework

---

## 🏛️ Architecture

### Module Structure

```
AppModule (Root)
│
├── BrowserModule
├── BrowserAnimationsModule
├── AppRoutingModule
├── HttpClientModule
├── AngularMaterialModule
└── Feature Modules
    ├── HomeModule
    ├── MovieListModule
    ├── DashboardModule
    └── SignInModule
```

### Dependency Injection

```typescript
// Services are provided at root level for singleton pattern
@Injectable({
  providedIn: 'root'
})
export class UserService { }

// Ensures single instance across entire application
```

### HTTP Interceptor Chain

```
HTTP Request
    ↓
SecurityInterceptor (adds headers, token)
    ↓
Backend API
    ↓
ErrorHandler (catches errors)
    ↓
Application
```

### Component Hierarchy

```
AppComponent
├── HomeComponent
├── MovieListComponent
│   └── MovieDetailsDialog
├── DashboardComponent
│   ├── DashboardCardComponent
│   ├── GenreDashboardComponent
│   └── LocationChartComponent
└── SignInDialogComponent
```

---

## 🧪 Testing

### Unit Tests

```bash
# Run unit tests
npm run test

# Run with code coverage
npm run test:coverage

# Run in watch mode
npm run test -- --watch
```

**Coverage Goals:**
- Statements: >80%
- Branches: >75%
- Functions: >80%
- Lines: >80%

### Test Structure

```
src/app/
├── components/
│   ├── my.component.ts
│   └── my.component.spec.ts  ← Test file
```

### Writing Tests

```typescript
// Example unit test
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MyComponent } from './my.component';

describe('MyComponent', () => {
  let component: MyComponent;
  let fixture: ComponentFixture<MyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(MyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display title', () => {
    const titleElement = fixture.nativeElement.querySelector('h1');
    expect(titleElement.textContent).toContain('Expected Title');
  });
});
```

---

## 📋 Contributing

### Guidelines

1. **Branch Naming**: Use conventional format
   ```
   feature/description
   bugfix/issue-name
   hotfix/critical-issue
   ```

2. **Commit Messages**: Follow conventional commits
   ```
   feat: add new feature
   fix: fix specific issue
   docs: update documentation
   style: format code
   refactor: improve code structure
   test: add tests
   ```

3. **Code Style**
   - Follow Angular style guide
   - Use meaningful variable names
   - Add comments for complex logic
   - Maximum line length: 120 characters

4. **Testing**
   - Write tests for new features
   - Maintain >80% test coverage
   - Run `npm run test` before pushing

5. **Security**
   - Run `npm audit` before committing
   - Fix all security vulnerabilities
   - Use `npm audit fix` when safe
   - Never commit secrets or credentials

### Pre-Commit Checklist

```
☐ Code builds without errors
☐ Tests passing (npm run test)
☐ No security vulnerabilities (npm audit)
☐ No console.log() debug statements
☐ Code formatted properly
☐ Commit message follows conventions
```

---

## 🐛 Troubleshooting

### Common Issues & Solutions

#### Issue: Dependencies not installing
```bash
# Clear npm cache and reinstall
rm -rf node_modules package-lock.json
npm cache clean --force
npm install
```

#### Issue: Port 4200 already in use
```bash
# Use different port
ng serve --port 4201
```

#### Issue: Module not found errors
```bash
# Ensure all imports are correct
# Check file paths and capitalization
# Rebuild the project
ng build --prod
```

#### Issue: CORS errors
```bash
# Ensure backend is running on correct port (8000)
# Check CORS configuration in backend
# Verify API URL in environment files
```

#### Issue: Authentication failing
```bash
# Verify JWT token is being stored
# Check browser DevTools > Application > localStorage
# Ensure SecurityInterceptor is registered in app.module.ts
# Check Network tab for Authorization header
```

#### Issue: Build size too large
```bash
# Analyze bundle size
npm run bundle-report

# Enable production optimization
ng build --configuration production --optimization
```

---

## 📚 Resources

### Documentation
- [Angular Documentation](https://angular.io/docs)
- [Angular CLI Documentation](https://angular.io/cli)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- [Angular Material Guide](https://material.angular.io/)
- [RxJS Documentation](https://rxjs.dev/)

### Security
- [Angular Security Guide](https://angular.io/guide/security)
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Content Security Policy](https://developer.mozilla.org/en-US/docs/Web/HTTP/CSP)
- [Security & Standards Guide](../SECURITY_AND_STANDARDS.md)

### Tools & Testing
- [Karma Test Runner](https://karma-runner.github.io/)
- [Jasmine Testing Framework](https://jasmine.github.io/)
- [Chrome DevTools](https://developer.chrome.com/docs/devtools/)

### Performance
- [Angular Performance Guide](https://angular.io/guide/performance-best-practices)
- [Web Vitals](https://web.dev/vitals/)
- [Lighthouse](https://developers.google.com/web/tools/lighthouse)

---

## 📞 Support & Contact

### Need Help?

1. **Check Existing Issues**: Search GitHub issues for similar problems
2. **Read Documentation**: See [DEVELOPER_QUICK_REFERENCE.md](../DEVELOPER_QUICK_REFERENCE.md)
3. **Security Issues**: Contact security@bookmyshow.com
4. **General Questions**: Create GitHub discussion or issue

### Contacts

| Role | Contact |
|------|---------|
| Frontend Lead | frontend@bookmyshow.com |
| Security Team | security@bookmyshow.com |
| Dev Team | dev-team@bookmyshow.com |

---

## 📄 License

This project is proprietary and confidential.

---

## 🎉 Getting Started Quickly

```bash
# 1. Install dependencies
npm install

# 2. Start development server
npm start

# 3. Open browser
# Navigate to http://localhost:4200

# 4. Start coding!
# Application will auto-reload on changes
```

---

**Version**: 1.0.0 | **Last Updated**: March 2026 | **Status**: ✅ Production Ready

For more information, see [SECURITY_AND_STANDARDS.md](../SECURITY_AND_STANDARDS.md) and [DEPLOYMENT_GUIDE.md](../DEPLOYMENT_GUIDE.md)
