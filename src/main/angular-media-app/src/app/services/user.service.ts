import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUserService } from '../auth/services/interfaces/iuser-service';
import { RegisterUser } from '../models/register-user';
import { LoginUser } from '../models/login-user';
import { SearchedUser } from '../models/searched-user';
import { AuthResponse } from '../models/auth-response';
import { BaseService } from './base-service';
import { environment } from '../../environment';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseService implements IUserService{

  private APIURL: string = environment.API_URL + "/auth/";
  private FriendRequestButtonURL: string = environment.API_URL + "/request/friendListWithIsFriend";
  private USER_CONTROLLER_APIURL: string = environment.API_URL + "/user";

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

  getById(userId: number): Observable<User>{
    return this.http.get<User>(
      `${this.USER_CONTROLLER_APIURL}/${userId}`,
      {
        headers: this.getHeaders()
      }
    );
  }

  update(id: number, user: User): Observable<HttpResponse<void>>{
    return this.http.put<void>(
      `${this.USER_CONTROLLER_APIURL}/${id}`,
      user,
      {
        headers: this.getHeaders(),
        observe: 'response'
      }
    );
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
