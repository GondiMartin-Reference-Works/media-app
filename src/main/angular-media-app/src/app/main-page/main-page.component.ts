import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { AuthService } from '../auth/services/auth.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent implements OnInit{

  createFormButton: boolean;

  constructor(
    private appComponent: AppComponent,
    private authService: AuthService) {
      this.createFormButton = false;
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()){
      this.appComponent.getAllUser();
    }
  }

  // Shows a brand-new form
  createForm(){
    this.createFormButton = true;
  }

  // Validates + sends + hides the form
  sendForm(){
    this.createFormButton = false;
  }

  // Hides the form
  closeForm(){
    this.createFormButton = false;
  }

}
