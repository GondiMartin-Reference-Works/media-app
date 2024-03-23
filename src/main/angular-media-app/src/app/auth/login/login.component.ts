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
    this.userService.login(this.user).subscribe((token) => {
      localStorage.setItem('current-user-token', token);
      this.authService.checkLoging();
      this.goToMainPage();
    })
  }

  goToMainPage(){
    this.router.navigate(['/main']);
  }
}
