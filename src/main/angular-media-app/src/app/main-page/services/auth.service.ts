import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isLoggedIn  = true;

  checkLoging(): void{
    this.isLoggedIn = localStorage.getItem("current-user-token") != null;
  }
}
