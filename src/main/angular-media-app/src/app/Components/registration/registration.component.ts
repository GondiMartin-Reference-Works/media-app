import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../../services/registration.service';
import { RegisterUser } from '../../models/register-user';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent implements OnInit {

  user: RegisterUser = new RegisterUser();

  constructor(
    private registrationService: RegistrationService
  ){ }

  ngOnInit(): void {
    
  }

  createUser(){
    this.registrationService.createUser(this.user).subscribe(()=>{
      alert("User Added Successfully");
      window.location.reload();
    })
  }
}
