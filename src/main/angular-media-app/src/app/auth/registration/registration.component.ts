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
    this.userService.create(this.user).subscribe((token) => {
      alert("User Added Successfully");
      console.log(token);
      this.goToLogin();
    })   
  }

  goToLogin(){
    this.router.navigate(['/login']);
  }
}
