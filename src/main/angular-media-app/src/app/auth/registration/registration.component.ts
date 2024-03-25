import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { RegisterUser } from '../../models/register-user';
import { Router } from '@angular/router';

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
    private router: Router
  ){
  }

  ngOnInit(): void { }

  createUser(){
    if(this.isValidInput()){
      this.userService.create(this.user).subscribe(() => {
        this.goToLogin();
      })
    }
  }

  goToLogin(){
    this.router.navigate(['/auth/login']);
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
