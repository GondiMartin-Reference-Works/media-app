import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { LoginUser } from '../../models/login-user';
import { AuthService } from '../../main-page/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  user: LoginUser = new LoginUser();

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ){
  }

  ngOnInit(): void {}

  logInUser(){
    if(this.isValidInput()){
      this.userService.login(this.user).subscribe((token) => {
        localStorage.setItem('current-user-token', token);
        this.goToMainPage();
      });
    }

  }

  goToMainPage(){
    this.router.navigate(['/main']);
  }

  isValidInput(): boolean{
    let email: string = this.user.email;
    let emailRegex: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if(!emailRegex.test(email)){
      alert("Invalid Email");
      throw new Error("Invalid Email");
    }

    let password: string = this.user.password;
    let passwordRegex: RegExp = /[a-zA-Z0-9]+/;
    if(!passwordRegex.test(password)){
      alert("Invalid Password");
      throw new Error("Invalid Password");
    }

    return true;
  }

}
