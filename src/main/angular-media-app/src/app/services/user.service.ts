import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUserService } from './interfaces/iuser-service';
import { RegisterUser } from '../models/register-user';

@Injectable({
  providedIn: 'root'
})
export class UserService implements IUserService{

  private APIUrl: string = "http://localhost:8080/api/v1/auth/register"

  constructor(
    private http: HttpClient
  ) { }

  create(newUser: RegisterUser): Observable<String>{
    return this.http.post<String>(this.APIUrl, newUser);
  }
}
