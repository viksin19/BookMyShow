import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserDetails } from '../modal-interfaces/userDetails.model';
import { environment } from '../../environments/environment';

/**
 * User authentication and profile service.
 * Handles user authentication, token management, and user profile operations.
 * 
 * Security Features:
 * - JWT token management
 * - Secure token storage
 * - Error handling and validation
 * - Logout functionality
 * 
 * @author BookMyShow Security Team
 * @version 2.0
 */
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  private apiUrl = `${environment.apiBaseUrl}${environment.endpoints.auth}`;

  /**
   * Authenticate user with credentials.
   * 
   * @param userId User identifier
   * @param userPassword User password
   * @returns Observable containing user details or error
   * @throws Error if userId or userPassword is empty
   */
  public authenticateUser(userId: string, userPassword: string): Observable<UserDetails | any> {
    // Input validation
    if (!userId || !userId.trim()) {
      return throwError(() => new Error('User ID is required'));
    }
    if (!userPassword || !userPassword.trim()) {
      return throwError(() => new Error('Password is required'));
    }

    // Sanitize input to prevent injection attacks
    const sanitizedUserId = this.sanitizeInput(userId);
    
    const reqBody = {
      'userId': sanitizedUserId,
      'userPassword': userPassword // Password is sent as-is over HTTPS
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(this.apiUrl, reqBody, { headers, responseType: 'json' }).pipe(
      map(response => {
        // Store auth token if provided
        if (response && response.token) {
          this.storeAuthToken(response.token);
          
          // Show token expiry warning if configured
          if (environment.features.enableLogging) {
            console.info('User authenticated successfully');
          }
        }
        return response;
      }),
      catchError((error: HttpErrorResponse) => {
        return this.handleAuthenticationError(error);
      })
    );
  }

  /**
   * Get stored authentication token.
   * 
   * @returns JWT token or null if not available
   */
  public getAuthToken(): string | null {
    const token = localStorage.getItem(environment.security.jwtTokenKey);
    return token && token.trim() ? token : null;
  }

  /**
   * Store authentication token securely.
   * 
   * @param token JWT token to store
   */
  public storeAuthToken(token: string): void {
    if (token && token.trim()) {
      localStorage.setItem(environment.security.jwtTokenKey, token);
    }
  }

  /**
   * Check if user is authenticated.
   * 
   * @returns true if valid token exists
   */
  public isAuthenticated(): boolean {
    const token = this.getAuthToken();
    return !!token && this.isTokenValid(token);
  }

  /**
   * Validate JWT token expiration.
   * 
   * @param token JWT token to validate
   * @returns true if token is valid and not expired
   */
  private isTokenValid(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expirationTime = payload.exp * 1000;
      return expirationTime > Date.now();
    } catch (error) {
      return false;
    }
  }

  /**
   * Clear authentication data on logout.
   */
  public logout(): void {
    localStorage.removeItem(environment.security.jwtTokenKey);
    localStorage.removeItem(environment.security.userInfoKey);
    if (environment.features.enableLogging) {
      console.info('User logged out successfully');
    }
  }

  /**
   * Store user information securely.
   * 
   * @param userInfo User information object
   */
  public storeUserInfo(userInfo: any): void {
    if (userInfo) {
      localStorage.setItem(environment.security.userInfoKey, JSON.stringify(userInfo));
    }
  }

  /**
   * Get stored user information.
   * 
   * @returns User information object or null
   */
  public getUserInfo(): any {
    const userInfo = localStorage.getItem(environment.security.userInfoKey);
    try {
      return userInfo ? JSON.parse(userInfo) : null;
    } catch (error) {
      console.error('Error parsing user info');
      return null;
    }
  }

  /**
   * Clear sensitive user data.
   */
  public clearUserData(): void {
    this.logout();
  }

  /**
   * Sanitize user input to prevent injection attacks.
   * 
   * @param input User input to sanitize
   * @returns Sanitized input
   */
  private sanitizeInput(input: string): string {
    if (!input) return '';
    
    // Remove potentially harmful characters while preserving valid usernames
    return input
      .trim()
      .substring(0, 50) // Limit length
      .replace(/[<>\"'%]/g, ''); // Remove HTML/SQL injection characters
  }

  /**
   * Handle authentication errors with appropriate messaging.
   * 
   * @param error HTTP error response
   * @returns Error observable
   */
  private handleAuthenticationError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Authentication failed';

    if (error.status === 401) {
      errorMessage = 'Invalid credentials';
    } else if (error.status === 400) {
      errorMessage = error.error?.message || 'Invalid request';
    } else if (error.status === 0) {
      errorMessage = 'Network error. Please check your connection.';
    } else {
      errorMessage = error.error?.message || 'An error occurred during authentication';
    }

    if (environment.features.enableLogging) {
      console.error('Authentication error:', errorMessage);
    }

    return throwError(() => ({
      message: errorMessage,
      status: error.status
    }));
  }
}
