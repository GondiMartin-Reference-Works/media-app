import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isLoggedIn(): boolean{
    return sessionStorage.getItem("current-user-token") != null;
  }
}
