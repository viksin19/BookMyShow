# BookMyShow - Secure Deployment Guide

**Version**: 2.0 | **Status**: Ready for Production | **Date**: March 2026

---

## 📋 Pre-Deployment Checklist

### 1. Security Review ✅

```
Security Checklist:
☐ All authentication endpoints protected
☐ Admin endpoints have @PreAuthorize("hasRole('ADMIN')")
☐ Input validation added (@Valid, @NotNull, @Size)
☐ No hardcoded secrets in code
☐ HTTPS configured
☐ CORS whitelist verified
☐ Error messages generic (no stack traces)
☐ Logging secured (no sensitive data)
☐ Dependencies updated to LTS versions
☐ Security headers configured
☐ Rate limiting implemented
☐ Database credentials encrypted
```

### 2. Code Quality

```
Code Quality Checklist:
☐ All tests passing (npm test, mvn test)
☐ Code coverage >80%
☐ No security warnings (npm audit, mvn check)
☐ No console.log() statements (frontend)
☐ No hardcoded URLs or ports
☐ Naming conventions followed
☐ Comments and documentation complete
☐ SonarQube analysis passed
☐ No TODO items left unresolved
```

### 3. Performance Testing

```
Performance Checklist:
☐ API response time <1s (normal cases)
☐ Login <500ms
☐ Database queries optimized
☐ No N+1 queries
☐ Connection pool sized correctly
☐ Cache strategy defined
☐ Load tested (100+ concurrent users)
```

---

## 🔧 Backend Deployment

### Step 1: Prepare Environment Variables

```bash
# Create .env file (NEVER commit this!)
cat > .env << EOF
# Spring Boot Configuration
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8000
SERVER_SERVLET_CONTEXT_PATH=/api

# Database
DB_URL=jdbc:mysql://prod-db.example.com:3306/bookmyshow
DB_USERNAME=app_user
DB_PASSWORD=$(openssl rand -base64 32)

# Security
JWT_SECRET=$(openssl rand -base64 32)
CORS_ORIGINS=https://bookmyshow.com,https://www.bookmyshow.com

# SSL/TLS
SSL_KEYSTORE_PATH=/etc/ssl/keystore.p12
SSL_KEYSTORE_PASSWORD=$(openssl rand -base64 32)

# Logging
LOGGING_LEVEL_ROOT=WARN
LOGGING_LEVEL_COM_BOOKMYSHOW=INFO
EOF

# Keep this file secure!
chmod 600 .env
```

### Step 2: Build Production JAR

```bash
cd EzeTapBackend/bookmyshow

# Clean build with security checks
mvn clean package \
  -DskipTests=false \
  -P prod \
  -Dcheckstyle.skip=false

# Verify JAR size (should be <200MB)
ls -lh target/bookmyshow-*.jar

# Scan for known vulnerabilities
mvn dependency-check:check
```

### Step 3: Docker Deployment (Recommended)

```dockerfile
# Dockerfile
FROM openjdk:17-slim

# Security: Run as non-root user
RUN useradd -m -u 1001 appuser

# Set working directory
WORKDIR /app

# Copy JAR
COPY target/bookmyshow-1.0.0-RELEASE.jar app.jar

# Change ownership
RUN chown -R appuser:appuser /app

USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD java -cp app.jar org.springframework.boot.loader.JarLauncher \
  -Dspring.profiles.active=prod \
  -Dhealth.check=true

# Run application
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", \
  "-Dspring.profiles.active=prod", \
  "-Dlogging.config=classpath:logback-prod.xml", \
  "-jar", "app.jar"]
```

### Step 4: Kubernetes Deployment (Optional)

```yaml
# kubernetes/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: bookmyshow-api
  namespace: production
spec:
  replicas: 3
  selector:
    matchLabels:
      app: bookmyshow-api
  template:
    metadata:
      labels:
        app: bookmyshow-api
    spec:
      # Security context
      securityContext:
        runAsNonRoot: true
        runAsUser: 1001
        fsReadOnlyRootFilesystem: true
        allowPrivilegeEscalation: false
      
      containers:
      - name: bookmyshow-api
        image: registry.example.com/bookmyshow:1.0.0
        
        # Environment variables from secrets
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: prod
        - name: JWT_SECRET
          valueFrom:
            secretKeyRef:
              name: bookmyshow-secrets
              key: jwt-secret
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: bookmyshow-secrets
              key: db-password
        
        # Resource limits
        resources:
          requests:
            memory: "256Mi"
            cpu: "250m"
          limits:
            memory: "512Mi"
            cpu: "500m"
        
        # Health checks
        livenessProbe:
          httpGet:
            path: /api/user/check
            port: 8000
          initialDelaySeconds: 30
          periodSeconds: 10
        
        readinessProbe:
          httpGet:
            path: /api/user/check
            port: 8000
          initialDelaySeconds: 20
          periodSeconds: 5
        
        # Volume for temp files
        volumeMounts:
        - name: temp
          mountPath: /tmp
      
      volumes:
      - name: temp
        emptyDir: {}
```

### Step 5: Direct Server Deployment

```bash
# SSH to production server
ssh ubuntu@prod-server.example.com

# Navigate to app directory
cd /opt/bookmyshow

# Load environment variables
source .env

# Start application with monitoring
nohup java \
  -Xmx1024m \
  -Xms512m \
  -Dspring.profiles.active=prod \
  -Dspring.config.location=classpath:/application-prod.properties \
  -jar bookmyshow-api.jar \
  > logs/application.log 2>&1 &

# Verify startup
tail -f logs/application.log | grep "Started BookMyShowApplication"

# Setup systemd service for auto-restart
sudo tee /etc/systemd/system/bookmyshow.service > /dev/null <<EOF
[Unit]
Description=BookMyShow API
After=network.target

[Service]
Type=simple
User=ubuntu
WorkingDirectory=/opt/bookmyshow
ExecStart=/usr/bin/java -Xmx1024m -Xms512m -Dspring.profiles.active=prod -jar bookmyshow-api.jar
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

sudo systemctl daemon-reload
sudo systemctl enable bookmyshow
sudo systemctl start bookmyshow
```

---

## 🎨 Frontend Deployment

### Step 1: Build Production Bundle

```bash
cd BookMyShowFrontEnd

# Install dependencies
npm ci  # Use ci instead of install for reproducible builds

# Security audit
npm audit fix

# Run tests
npm run test:ci

# Build production bundle
ng build --configuration production \
  --optimization \
  --build-optimizer \
  --aot \
  --vendor-chunk \
  --extract-css

# Verify bundle size
npm run bundle-report

# Expected sizes:
# - main bundle: <500KB
# - vendor bundle: <300KB
# - total: <1MB (gzipped <200KB)
```

### Step 2: Static File Deployment (nginx)

```nginx
# nginx.conf
upstream api_backend {
    server bookmyshow-api:8000;
    keepalive 32;
}

server {
    listen 80;
    server_name _;
    
    # Redirect HTTP to HTTPS
    return 301 https://$host$request_uri;
}

server {
    listen 443 ssl http2;
    server_name bookmyshow.com www.bookmyshow.com;
    
    # SSL Configuration
    ssl_certificate /etc/ssl/certs/certificate.crt;
    ssl_certificate_key /etc/ssl/private/private.key;
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers on;
    
    # Security Headers
    add_header Strict-Transport-Security "max-age=31536000; includeSubDomains" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-Frame-Options "DENY" always;
    add_header X-XSS-Protection "1; mode=block" always;
    add_header Content-Security-Policy "default-src 'self'" always;
    add_header Referrer-Policy "strict-origin-when-cross-origin" always;
    
    # Gzip compression
    gzip on;
    gzip_types text/plain text/css text/javascript application/json;
    gzip_min_length 1024;
    
    # Serve static files
    root /usr/share/nginx/html;
    
    # Angular routing
    location / {
        try_files $uri $uri/ /index.html;
    }
    
    # Cache buster for main assets
    location ~* ^/assets/ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }
    
    # Don't cache index.html
    location = /index.html {
        expires -1;
        add_header Cache-Control "no-store, no-cache, must-revalidate";
    }
    
    # Proxy API requests
    location /api/ {
        proxy_pass http://api_backend;
        proxy_http_version 1.1;
        proxy_set_header Connection "";
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # Timeouts
        proxy_connect_timeout 60s;
        proxy_send_timeout 60s;
        proxy_read_timeout 60s;
    }
}
```

### Step 3: Docker Frontend Deployment

```dockerfile
# Dockerfile (Multi-stage)
FROM node:18-alpine AS builder

WORKDIR /app
COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build --prod

# Production stage
FROM nginx:alpine

COPY --from=builder /app/dist/book-my-show /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80 443

CMD ["nginx", "-g", "daemon off;"]
```

### Step 4: CDN Deployment (Cloudflare/AWS CloudFront)

```bash
# Push dist folder to S3
aws s3 sync dist/book-my-show s3://bookmyshow-cdn \
  --delete \
  --cache-control "public, max-age=1y" \
  --exclude "index.html" \
  --exclude "*.js.map"

# Don't cache index.html
aws s3 cp dist/book-my-show/index.html s3://bookmyshow-cdn/index.html \
  --cache-control "no-cache, no-store, must-revalidate" \
  --content-type "text/html"

# Invalidate CloudFront
aws cloudfront create-invalidation \
  --distribution-id E123ABC \
  --paths "/*"
```

---

## ✅ Post-Deployment Verification

### Health Checks

```bash
# Check backend health
curl -k https://api.bookmyshow.com/api/user/check
# Expected: 200 OK with "bookmyshow service is working."

# Check frontend
curl -s https://bookmyshow.com | grep "<title>"

# Check HTTPS
curl -I https://bookmyshow.com | grep "Strict-Transport-Security"

# Check API availability
curl -k https://api.bookmyshow.com/api/movie/landingdata

# Monitor logs
tail -f /opt/bookmyshow/logs/application.log
```

### Security Verification

```bash
# Verify SSL certificate
openssl s_client -connect api.bookmyshow.com:443

# Check security headers
curl -I https://api.bookmyshow.com/api/user/check | grep -i "security\|strict\|x-frame"

# Test CORS
curl -H "Origin: https://evil.com" \
     -H "Access-Control-Request-Method: POST" \
     -H "Access-Control-Request-Headers: content-type" \
     -X OPTIONS https://api.bookmyshow.com/api/user/auth -v

# Check for exposed .git folder
curl https://bookmyshow.com/.git/config

# Verify CSP headers
curl -I https://api.bookmyshow.com/api/user/check | grep "Content-Security-Policy"
```

### Performance Checks

```bash
# Load test (use Apache Bench or similar)
ab -n 1000 -c 10 https://api.bookmyshow.com/api/user/check

# Database performance
# In backend logs, check query times (should be <100ms)

# Frontend bundle analysis
npm run bundle-report

# Lighthouse audit
lighthouse https://bookmyshow.com --output-path ./report.html
```

---

## 🚨 Continuous Monitoring

### Set up Monitoring Alerts

```yaml
# monitoring/alerts.yaml
alerts:
  - name: HighErrorRate
    condition: error_rate > 1%
    action: notify_team
  
  - name: LongResponseTime
    condition: p95_response_time > 5000ms
    action: page_oncall
  
  - name: FailedLogin
    condition: failed_logins > 10 per hour
    action: notify_security
  
  - name: DatabaseDown
    condition: db_connection_fail
    action: page_oncall && notify_team
  
  - name: LowDiskSpace
    condition: disk_usage > 90%
    action: notify_ops
```

### Log Aggregation

```bash
# Ensure logs are being collected
journalctl -u bookmyshow -f  # System logs

tail -f /opt/bookmyshow/logs/application.log  # App logs

# Forward to ELK/Splunk
# Configuration should send all logs to central system
```

---

## 🔄 Rollback Procedure

### If Issues Occur

```bash
# Stop current version
sudo systemctl stop bookmyshow

# Backup current version
cp -r /opt/bookmyshow /opt/bookmyshow-backup-$(date +%s)

# Restore previous version
cp /opt/bookmyshow-previous/bookmyshow-api.jar /opt/bookmyshow/

# Restart
sudo systemctl start bookmyshow

# Verify
curl https://api.bookmyshow.com/api/user/check

# If OK, notify team
# If NOT OK, escalate to senior developer
```

---

## 📊 Performance Baselines (Post-Deployment)

Expected metrics:
- **Login Response**: <500ms
- **API Response**: <1s (p95)
- **Database Query**: <100ms
- **Error Rate**: <0.1%
- **Availability**: >99.9%
- **CPU Usage**: <40%
- **Memory Usage**: <60%

---

## 🎯 Success Criteria

Deployment is successful when:
- ✅ All health checks passing
- ✅ No security warnings in logs
- ✅ User authentication working
- ✅ API endpoints responding correctly
- ✅ Frontend loading in <2s
- ✅ Database operations <100ms
- ✅ Error rate <0.1%
- ✅ HTTPS/SSL certificate valid
- ✅ All security headers present
- ✅ No sensitive data in logs

---

**Document Version**: 2.0 | **Last Updated**: March 2026 | **Status**: ✅ Ready
