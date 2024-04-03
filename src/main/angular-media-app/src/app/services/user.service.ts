import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUserService } from '../auth/services/interfaces/iuser-service';
import { RegisterUser } from '../models/register-user';
import { LoginUser } from '../models/login-user';
import { SearchedUser } from '../models/searched-user';
import { AuthResponse } from '../models/auth-response';
import { FriendRequest } from '../models/friend-request';
import { Emails } from '../models/email';

@Injectable({
  providedIn: 'root'
})
export class UserService implements IUserService{

  private APIURL: string = "http://localhost:8080/api/v1/auth/";
  private APIUserURL: string = "http://localhost:8080/api/v1/user";
  private FriendRequestButtonURL: string = "http://localhost:8080/request/friendListWithIsFriend";
  public headers : HttpHeaders = new HttpHeaders();

  constructor(
    private http: HttpClient
  ) {}

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
    this.validateHeaders();
    const params : HttpParams = new HttpParams().set('email', JSON.parse(sessionStorage.getItem('current-user-email') ?? ''));
    return this.http.get<SearchedUser[]>(this.FriendRequestButtonURL, { headers: this.headers, params: params });
  }

  validateHeaders(): void{
    const json = sessionStorage.getItem("current-user-token") ?? "";
    if (json != ""){
      const auth_token = JSON.parse(json);
      this.headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth_token}`
      });
    }
  }

  


}
