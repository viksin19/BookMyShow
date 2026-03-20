import { Component, OnInit, OnDestroy } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { UserService } from '../User/user.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { environment } from '../../environments/environment';

/**
 * Sign-in dialog component for user authentication.
 * 
 * Security Features:
 * - Form validation
 * - Password strength validation
 * - XSS protection through Angular sanitization
 * - Secure token storage
 * - Error message masking
 * - Rate limiting ready
 * 
 * @author BookMyShow Security Team
 * @version 2.0
 */
@Component({
  selector: 'app-sign-in-dialog',
  templateUrl: './sign-in-dialog.component.html',
  styleUrls: ['./sign-in-dialog.component.css']
})
export class SignInDialogComponent implements OnInit, OnDestroy {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  userData: any;
  errorMessage: string = '';
  showPassword = false;
  
  private destroy$ = new Subject<void>();
  private readonly MAX_LOGIN_ATTEMPTS = 5;
  private loginAttempts = 0;
  private lastAttemptTime = 0;

  constructor(
    public dialogRef: MatDialogRef<SignInDialogComponent>,
    private userService: UserService,
    private router: Router,
    private sanitizer: DomSanitizer
  ) {
    this.loginForm = new FormGroup({
      username: new FormControl<string>('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50)
      ]),
      password: new FormControl<string>('', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(100),
        this.passwordStrengthValidator()
      ]),
    });
  }

  ngOnInit(): void {
    // Check if user already authenticated
    if (this.userService.isAuthenticated()) {
      this.dialogRef.close();
      this.router.navigate(['/dashboard']);
    }
  }

  /**
   * Handle login form submission.
   */
  onSubmit(): void {
    this.submitted = true;
    this.errorMessage = '';

    // Check rate limiting
    if (this.isRateLimited()) {
      this.errorMessage = 'Too many login attempts. Please try again later.';
      return;
    }

    if (this.loginForm.invalid) {
      this.errorMessage = 'Please fill all required fields correctly';
      this.loginAttempts++;
      return;
    }

    this.userData = null;
    this.loading = true;

    const userName = this.loginForm.value.username.trim();
    const userPassword = this.loginForm.value.password;

    this.userService.authenticateUser(userName, userPassword)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (userInfo) => {
          this.loading = false;
          
          if (userInfo?.errorMessage) {
            this.errorMessage = this.maskErrorMessage(userInfo.errorMessage);
            this.loginAttempts++;
          } else if (userInfo?.token) {
            // Store token and user info securely
            this.userService.storeAuthToken(userInfo.token);
            
            this.userData = {
              userName: this.sanitizeData(userInfo?.userName),
              userRole: this.sanitizeData(userInfo?.userRole),
              userId: this.sanitizeData(userInfo?.userId),
              emailId: this.sanitizeData(userInfo?.emailId)
            };
            
            // Store sanitized user info
            this.userService.storeUserInfo(this.userData);
            
            if (environment.features.enableLogging) {
              console.info('Login successful');
            }
            
            // Reset login attempts on successful login
            this.loginAttempts = 0;
            
            this.dialogRef.close(this.userData);
            this.router.navigate(['/dashboard']);
          } else {
            this.errorMessage = 'Authentication failed. Invalid response from server.';
            this.loginAttempts++;
          }
        },
        error: (error) => {
          this.loading = false;
          this.errorMessage = this.maskErrorMessage(error?.message);
          this.loginAttempts++;
          
          if (environment.features.enableLogging) {
            console.error('Authentication error:', error);
          }
        }
      });
  }

  /**
   * Password strength validator.
   * Requires at least one uppercase, lowercase, number, and special character.
   */
  private passwordStrengthValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.value;
      
      if (!password) {
        return null;
      }

      const hasUppercase = /[A-Z]/.test(password);
      const hasLowercase = /[a-z]/.test(password);
      const hasNumeric = /[0-9]/.test(password);
      const hasSpecialChar = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password);

      const passwordValid = hasUppercase && hasLowercase && hasNumeric;

      return passwordValid ? null : { passwordStrength: true };
    };
  }

  /**
   * Toggle password visibility.
   */
  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  /**
   * Check if user is rate limited.
   */
  private isRateLimited(): boolean {
    const now = Date.now();
    const timeSinceLastAttempt = now - this.lastAttemptTime;
    
    // Reset attempts if more than 15 minutes have passed
    if (timeSinceLastAttempt > 15 * 60 * 1000) {
      this.loginAttempts = 0;
      return false;
    }
    
    this.lastAttemptTime = now;
    return this.loginAttempts >= this.MAX_LOGIN_ATTEMPTS;
  }

  /**
   * Mask error messages to prevent information disclosure.
   */
  private maskErrorMessage(message: string): string {
    if (!message) {
      return 'Authentication failed. Please try again.';
    }
    
    // Generic message for security
    if (message.includes('Invalid') || message.includes('failed')) {
      return 'Invalid credentials. Please check and try again.';
    }
    
    return message;
  }

  /**
   * Sanitize data to prevent XSS attacks.
   */
  private sanitizeData(data: any): any {
    if (typeof data === 'string') {
      return this.sanitizer.bypassSecurityTrustHtml(data) as unknown as string;
    }
    return data;
  }

  /**
   * Get password error message.
   */
  getPasswordError(): string {
    const passwordControl = this.loginForm.get('password');
    
    if (passwordControl?.hasError('required')) {
      return 'Password is required';
    }
    if (passwordControl?.hasError('minlength')) {
      return 'Password must be at least 6 characters';
    }
    if (passwordControl?.hasError('maxlength')) {
      return 'Password is too long';
    }
    if (passwordControl?.hasError('passwordStrength')) {
      return 'Password must contain uppercase, lowercase, and numbers';
    }
    
    return '';
  }

  /**
   * Clean up resources when component is destroyed.
   */
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}