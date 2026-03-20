// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

/**
 * Development environment configuration.
 * Contains API endpoints, feature flags, and development settings.
 */
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8000/api',
  
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
    enableLogging: true,
    debugMode: true,
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

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
