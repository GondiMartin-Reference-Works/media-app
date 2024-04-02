import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUserService } from '../auth/services/interfaces/iuser-service';
import { RegisterUser } from '../models/register-user';
import { LoginUser } from '../models/login-user';
import { SearchedUser } from '../models/searched-user';
import { AuthResponse } from '../models/auth-response';
import { BaseService } from './base-service';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseService implements IUserService{

  private APIURL: string = "http://localhost:8080/api/v1/auth/";
  private APIUserURL: string = "http://localhost:8080/api/v1/user";

  constructor(
    private http: HttpClient
  ) {
    super();
  }

  create(newUser: RegisterUser): Observable<AuthResponse>{
    return this.http.post<AuthResponse>(this.APIURL + "register", newUser);
  }

  login(user: LoginUser): Observable<AuthResponse>{
    return this.http.post<AuthResponse>(this.APIURL + "authenticate", user);
  }

  logout(){
    sessionStorage.removeItem("current-user-token");
    window.location.reload();
  }

  getAll(): Observable<SearchedUser[]>{
    return this.http.get<SearchedUser[]>(this.APIUserURL, { headers: this.getHeaders()});
  }
}
