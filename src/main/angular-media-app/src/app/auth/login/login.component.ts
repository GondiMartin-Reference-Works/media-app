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

    if(this.isValidInput()){
      this.userService.login(this.user).subscribe((token) => {
        alert("User Logged Successfully");
        console.log(token);
        localStorage.setItem('current-user-token', token);
        window.location.reload();
      })
    }
  }

  goToMainPage(){
    this.router.navigate(['/main-page']);
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
