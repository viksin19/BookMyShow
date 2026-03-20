import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, finalize, timeout } from 'rxjs/operators';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

/**
 * HTTP Interceptor for security and request/response handling.
 * 
 * Features:
 * - Adds authorization Bearer token to requests
 * - Adds security headers (X-Requested-With, CSRF-Token)
 * - Centralized error handling
 * - Request timeout handling
 * - Automatic unauthorized handling
 * - Logging support
 * 
 * @author BookMyShow Security Team
 * @version 2.0
 */
@Injectable()
export class SecurityInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
    // Clone request and add security headers
    let secureRequest = this.addSecurityHeaders(request);

    // Add authorization token if available
    const token = this.getAuthToken();
    if (token && this.isJwtValid(token)) {
      secureRequest = secureRequest.clone({
        setHeaders: {
          'Authorization': `Bearer ${token}`
        }
      });
    } else if (token) {
      // Token is invalid, clear it
      this.clearAuthStorage();
    }

    if (environment.features.debugMode) {
      console.log(`[Security Interceptor] Request to: ${request.url}`);
    }

    return next.handle(secureRequest).pipe(
      timeout(environment.timeout.httpRequest),
      catchError((error: any) => {
        return this.handleError(error);
      }),
      finalize(() => {
        // Cleanup after request completes
        if (environment.features.debugMode) {
          console.log(`[Security Interceptor] Request completed for: ${request.url}`);
        }
      })
    );
  }

  /**
   * Add security headers to request.
   */
  private addSecurityHeaders(request: HttpRequest<any>): HttpRequest<any> {
    return request.clone({
      setHeaders: {
        'X-Requested-With': 'XMLHttpRequest',
        'Content-Type': request.headers.has('Content-Type') ? 
          request.headers.get('Content-Type')! : 'application/json',
        'X-CSRF-Token': this.getCsrfToken() || ''
      }
    });
  }

  /**
   * Centralized error handling.
   */
  private handleError(error: any): Observable<never> {
    let errorMessage = 'An error occurred';

    if (error.name === 'TimeoutError') {
      errorMessage = 'Request timeout. Please try again later.';
      console.error('[Security Interceptor] Request timeout:', error);
    } else if (error instanceof HttpErrorResponse) {
      if (error.error instanceof ErrorEvent) {
        // Client-side error
        errorMessage = `Error: ${error.error.message}`;
        console.error('[Security Interceptor] Client-side error:', error);
      } else {
        // Server-side error
        errorMessage = this.getErrorMessage(error);
        console.error('[Security Interceptor] Server-side error:', error);

        // Handle specific error codes
        this.handleErrorByStatus(error.status);
      }
    } else {
      errorMessage = error.message || 'An unexpected error occurred';
      console.error('[Security Interceptor] Unexpected error:', error);
    }

    return throwError(() => ({
      message: errorMessage,
      error: error
    }));
  }

  /**
   * Get appropriate error message based on HTTP status.
   */
  private getErrorMessage(error: HttpErrorResponse): string {
    switch (error.status) {
      case 400:
        return error.error?.message || 'Bad request. Please check your input.';
      case 401:
        return 'Session expired. Please log in again.';
      case 403:
        return 'You do not have permission to access this resource.';
      case 404:
        return 'The requested resource was not found.';
      case 500:
        return 'An internal server error occurred. Please try again later.';
      case 503:
        return 'Service temporarily unavailable. Please try again later.';
      default:
        return `Error: ${error.statusText || 'Unknown error'}`;
    }
  }

  /**
   * Handle specific HTTP status codes.
   */
  private handleErrorByStatus(status: number): void {
    switch (status) {
      case 401:
        // Unauthorized - redirect to login
        this.clearAuthStorage();
        this.router.navigate(['/']);
        break;
      case 403:
        // Forbidden
        this.router.navigate(['/']);
        break;
    }
  }

  /**
   * Get authorization token from storage.
   * Prefers localStorage but can fallback to other storage methods.
   */
  private getAuthToken(): string | null {
    const token = localStorage.getItem(environment.security.jwtTokenKey);
    return token && token.trim() ? token : null;
  }

  /**
   * Get CSRF token from storage or DOM meta tag.
   */
  private getCsrfToken(): string | null {
    const token = localStorage.getItem('csrfToken');
    if (token) return token;

    const metaTag = document.querySelector('meta[name="csrf-token"]');
    return metaTag ? metaTag.getAttribute('content') : null;
  }

  /**
   * Validate JWT token expiration.
   * Checks if token will expire within warning threshold.
   */
  private isJwtValid(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expirationTime = payload.exp * 1000;
      const currentTime = Date.now();
      
      // Token is valid if expiration is in the future
      return expirationTime > currentTime;
    } catch (error) {
      return false;
    }
  }

  /**
   * Clear authentication storage and logout.
   */
  private clearAuthStorage(): void {
    localStorage.removeItem(environment.security.jwtTokenKey);
    localStorage.removeItem(environment.security.userInfoKey);
  }
}
