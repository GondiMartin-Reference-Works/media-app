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

  
}
