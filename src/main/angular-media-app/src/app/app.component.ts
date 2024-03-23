import { Component, OnInit } from '@angular/core';
import { UserService } from './auth/services/user.service';
import { AuthService } from './main-page/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent{
  title = 'angular-media-app';

  constructor(
    public userService: UserService,
    public authService: AuthService){
  }
}
