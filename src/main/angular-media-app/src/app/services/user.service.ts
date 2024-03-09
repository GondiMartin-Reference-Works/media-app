import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUserService } from './interfaces/iuser-service';
import { RegisterUser } from '../models/register-user';

@Injectable({
  providedIn: 'root'
})
export class UserService implements IUserService{

  private RegisterAPIUrl: string = "http://localhost:8080/api/v1/auth/register"
  private LoginAPIUrl: string = "http://localhost:8080/api/v1/auth/authenticate"

  constructor(
    private http: HttpClient
  ) { }

  create(newUser: RegisterUser): Observable<string>{
    return this.http.post<string>(this.RegisterAPIUrl, newUser);
  }

  login(user: RegisterUser): Observable<string>{
    return this.http.post<string>(this.LoginAPIUrl, user);
  }
}
