import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { LoginUser } from '../../models/login-user';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorLoginComponent } from '../registration/error-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent{
  user: LoginUser = new LoginUser();

  constructor(
    private userService: UserService,
    private router: Router,
    private dialog: MatDialog
  ){
  }

  ngOnInit(): void {}

  logInUser(){
    this.userService.login(this.user).subscribe((response) => {
      const tokenJson = JSON.stringify(response.token);
      sessionStorage.setItem('current-user-token', tokenJson);
      response.user.birthDate = new Date(response.user.birthDate);
      const userJson = JSON.stringify(response.user);
      sessionStorage.setItem('current-user', userJson);
      sessionStorage.setItem('current-user-email', JSON.stringify(this.user.email));
      this.goToMainPage();
    },
    (error: HttpErrorResponse) => {
      if(error.status === 401){
        this.dialog.open(ErrorLoginComponent);
      }
    }
  );
  }

  goToMainPage(){
    this.router.navigate(['/main']);
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

}
