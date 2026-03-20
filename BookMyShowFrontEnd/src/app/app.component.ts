import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSidenav } from '@angular/material/sidenav';
import { UserDetails } from './modal-interfaces/userDetails.model';
import { SignInDialogComponent } from './sign-in-dialog/sign-in-dialog.component';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'BookMyShow';
  isSignIn: boolean = false;
  showSignInForm: boolean = false;
  userInfo: UserDetails | undefined;
  private destroy$ = new Subject<void>();

  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;

  constructor(public dialog: MatDialog) {

  }

  ngOnInit(): void {
    this.checkExistingSession();
    this.sidenav.mode = 'over';
    this.sidenav.close();
  }

  /**
   * Check if user session exists from localStorage.
   */
  private checkExistingSession(): void {
    const storedUserInfo = localStorage.getItem('userInfo');
    if (storedUserInfo) {
      try {
        this.userInfo = JSON.parse(storedUserInfo);
        this.isSignIn = true;
        this.sidenav.mode = 'side';
        this.sidenav.open();
      } catch (error) {
        // Clear corrupted session data
        localStorage.removeItem('userInfo');
      }
    }
  }

  /**
   * Open sign-in dialog.
   */
  openSignInFormDialog(): void {
    const dialogRef = this.dialog.open(SignInDialogComponent);

    dialogRef.afterClosed()
      .pipe(takeUntil(this.destroy$))
      .subscribe(result => {
        if (!!result) {
          this.userInfo = result;
          this.isSignIn = true;
          this.sidenav.mode = 'side';
          this.sidenav.open();
        }
      });
  }

  /**
   * Logout user and clear session.
   */
  public logout(): void {
    this.isSignIn = false;
    this.userInfo = undefined;
    localStorage.removeItem('userInfo');
    localStorage.removeItem('authToken');
    this.sidenav.close();
  }

  /**
   * Clean up resources when component is destroyed.
   */
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
