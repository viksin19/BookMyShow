/**
 * Production environment configuration.
 * Contains production API endpoints and settings.
 */
export const environment = {
  production: true,
  apiBaseUrl: 'https://api.bookmyshow.com/api',
  
  // Security configuration
  security: {
    jwtTokenKey: 'authToken',
    userInfoKey: 'userInfo',
    tokenExpiryWarningThreshold: 300000, // 5 minutes in milliseconds
  },
  
  // API endpoints
  endpoints: {
    auth: '/user/auth',
    register: '/user/register',
    checkHealth: '/user/check',
    movies: '/movie',
    dashboard: '/dashboard',
  },
  
  // Feature flags
  features: {
    enableLogging: false,
    debugMode: false,
  },
  
  // CORS configuration
  cors: {
    withCredentials: false,
  },
  
  // Timeout settings (in milliseconds)
  timeout: {
    httpRequest: 30000,
  }
};
