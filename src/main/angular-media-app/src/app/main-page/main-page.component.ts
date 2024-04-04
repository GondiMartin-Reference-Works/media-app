import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { AuthService } from '../auth/services/auth.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent implements OnInit{

  constructor(
    private appComponent: AppComponent,
    private authService: AuthService) { }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()){
      this.appComponent.getAllUser();
    }
  }



}
