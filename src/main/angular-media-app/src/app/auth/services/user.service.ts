import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUserService } from './interfaces/iuser-service';
import { RegisterUser } from '../../models/register-user';
import { LoginUser } from '../../models/login-user';

@Injectable({
  providedIn: 'root'
})
export class UserService implements IUserService{

  private APIURL: string = "http://localhost:8080/api/v1/auth/";

  constructor(
    private http: HttpClient
  ) { }

  create(newUser: RegisterUser): Observable<string>{
    return this.http.post<string>(this.APIURL + "register", newUser);
  }

  login(user: LoginUser): Observable<string>{
    return this.http.post<string>(this.APIURL + "authenticate", user);
  }

  logout(){
    localStorage.removeItem("current-user-token");
    window.location.reload();
  }
}
