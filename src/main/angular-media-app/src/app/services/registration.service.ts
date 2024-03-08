import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private APIUrl: string = "https://localhost:3306/database/valami"

  constructor(
    private http: HttpClient
  ) { }

  createUser(data: any): Observable<any> {
    return this.http.post(this.APIUrl, data);
  }
}
