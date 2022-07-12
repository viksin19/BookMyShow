import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserService } from '../User/user.service';

@Component({
  selector: 'sign-in-dailog',
  templateUrl: './sign-in-dailog.component.html',
  styleUrls: ['./sign-in-dailog.component.css']
})
export class SignInDailogComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  userData: any;

  //returnUrl: string;
  constructor(public dialogRef: MatDialogRef<SignInDailogComponent>,
    private userService:UserService,
    private route:Router) {
    this.loginForm = new FormGroup({
      username: new FormControl<string | any>('', Validators.required),
      password: new FormControl<string | any>('', Validators.required),
    });
  }

  ngOnInit(): void {
  }
  onSubmit() {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }
    this.userData = null;
    console.log(this.loginForm.value);
    this.loading = true;
    const userName = this.loginForm.value.username;
    const userPassword = this.loginForm.value.password;
    this.userService.authenticateUser(userName,userPassword).subscribe(userInfo=>{
      //var userRes = userInfo;
      
      if(userInfo?.errorMessage){
        alert(userInfo?.errorMessage);
      }else{ this.userData = {
        userName: userInfo?.userName,
        userRole: userInfo?.userRole,
        userId:  userInfo?.userId,
        emailId: userInfo?.emailId
      };
      this.dialogRef.close(this.userData);
      this.route.navigate(['dashboard']);
    }
      this.loading = false;
    })
 
  }
}

