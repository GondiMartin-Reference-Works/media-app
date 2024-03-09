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
    this.getAllUsers();
  }

  ngOnInit(): void { }

  getAllUsers(){
    this.userService.getAll().subscribe((response : RegisterUser[])=>{
      this.userList = response;
    });
  }

  createUser(){
    if(!this.validateUserEmailAddressIsUsed(this.user.email)){
      this.userService.create(this.user).subscribe(() => {
        alert("User Added Successfully");
        this.validEmailAddress = true;
        this.goToLogin();
      })
    }else{
      alert("Email address has been already used");
      this.validEmailAddress = false;
    }    
  }

  goToLogin(){
    this.router.navigate(['/login']);
  }

  validateUserEmailAddressIsUsed(email: string): boolean{
    return this.userList.map(u => u.email).indexOf(email) > -1;
  }
}
