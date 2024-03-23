import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isLoggedIn  = false;

  checkLoging(): void{
    this.isLoggedIn = localStorage.getItem("current-user-token") != null;
  }
  // redirectUrl: string | null = null;

  // login(): void {
  //   this.isLoggedIn = true;
  // }

  // logout(): void {
  //   this.isLoggedIn = false;
  //   window.location.reload();
  // }
}
