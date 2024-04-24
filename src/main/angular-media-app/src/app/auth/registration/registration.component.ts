import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { RegisterUser } from '../../models/register-user';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorDialogComponent } from './error-dialog.component';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent implements OnInit {

  user: RegisterUser = new RegisterUser();
  validEmailAddress: boolean = true;

  constructor(
    private userService: UserService,
    private router: Router,
    private dialog: MatDialog
  ){
  }

  ngOnInit(): void { }

  createUser(){
      this.userService.create(this.user).subscribe(() => {
        this.goToLogin();
      },
      (error: HttpErrorResponse) =>{
        if(error.status === 409){
          this.dialog.open(ErrorDialogComponent);
        }
      }
    )
  }




  goToLogin(){
    this.router.navigate(['/auth/login']);
  }

  isValidName(name: string): boolean{
    let nameRegex: RegExp = /^[\p{L}\p{M} ]+$/u;
    if(!nameRegex.test(name)){
      return false;
    }
    return true;
  }

  isValidEmail(): boolean{
    let email: string = this.user.email;
    let emailRegex: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if(!emailRegex.test(email)){
      return false;
    }
    return true;
  }

  isValidPassword(): boolean{
    let password: string = this.user.password;
    let passwordRegex: RegExp = /[a-zA-Z0-9]+/;
    if(!passwordRegex.test(password)){
      return false;
    }

    return true;
  }

  isAllValid(): boolean{
    return this.isValidName(this.user.firstName)
    && this.isValidName(this.user.lastName)
    && this.isValidEmail()
    && this.isValidPassword();
  }
}
