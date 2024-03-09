import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUserService } from './interfaces/iuser-service';
import { RegisterUser } from '../models/register-user';

@Injectable({
  providedIn: 'root'
})
export class UserService implements IUserService{

  private APIUrl: string = "https://localhost:3306/database/valami"

  constructor(
    private http: HttpClient
  ) { }

  create(newUser: RegisterUser): Observable<RegisterUser>{
    return this.http.post<RegisterUser>(this.APIUrl, newUser);
  }
}
