import { Component } from '@angular/core';
import { RegisterUser } from '../../models/register-user';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  user: RegisterUser = new RegisterUser();

  constructor(
    private userService: UserService,
    private router: Router
  ){
  }

  ngOnInit(): void {}

  loginUser(){
    this.userService.login(this.user).subscribe((token) => {
      alert("User Logged Successfully");
      console.log(token);
      localStorage.setItem('current-user-token', token);
      window.location.reload();
    })
  }

  goToMainPage(){
    this.router.navigate(['/main-page']);
  }
}
