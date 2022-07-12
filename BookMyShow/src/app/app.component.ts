import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSidenav } from '@angular/material/sidenav';
import { UserDetails } from './modal-interfaces/userDetails.model';
import { SignInDailogComponent } from './sign-in-dailog/sign-in-dailog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'BookMyShow';
  isSignIn:boolean = false;
  showSignInForm:boolean = false;
  userInfo:UserDetails|undefined;

  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;

  constructor(public dialog: MatDialog){

  }
  ngOnInit(): void {
    console.log('SignIn ? :',this.isSignIn);
    this.sidenav.mode = 'over';
    this.sidenav.close();
  }

  openSignInFormDialog():void{
    
    console.log(this.isSignIn);
     const dialogRef = this.dialog.open(SignInDailogComponent);

     dialogRef.afterClosed().subscribe(result => {
      if(!!result){
        this.userInfo = result;
        this.isSignIn = true;
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
       console.log(result);
     });
  }

  

}
