import { HttpClient, HttpParams } from '@angular/common/http';
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
  private FriendRequestButtonURL: string = "http://localhost:8080/request/friendListWithIsFriend";

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
    sessionStorage.removeItem("current-user-email");
    window.location.reload();
  }

  getAll(): Observable<SearchedUser[]>{
    const params : HttpParams = new HttpParams().set('email', JSON.parse(sessionStorage.getItem('current-user-email') ?? ''));
    return this.http.get<SearchedUser[]>(this.FriendRequestButtonURL, { headers: this.getHeaders(), params: params });
  }
}
