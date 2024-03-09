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
  userList: RegisterUser[] = [];
  validEmailAddress: boolean = true;

  constructor(
    private userService: UserService,
    private router: Router
  ){ 
  }

  ngOnInit(): void { }

  createUser(){
    this.userService.create(this.user).subscribe(() => {
      alert("User Added Successfully");
      this.goToLogin();
    })   
  }

  goToLogin(){
    this.router.navigate(['/login']);
  }
}
