import { Injectable } from '@angular/core';
import { BaseService } from './base-service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Address } from '../models/address';
import { environment } from '../../environment';

@Injectable({
  providedIn: 'root'
})
export class AddressService extends BaseService {

  private APIURL: string = environment.API_URL + "/address";

  constructor(
    private http: HttpClient
  ) {
    super();
  }

  getAll(userId: number): Observable<Address[]> {
    const params: HttpParams = new HttpParams().set('userId', userId);
    return this.http.get<Address[]>(
      this.APIURL,
      {
        headers: this.getHeaders(),
        params: params
      }
    );
  }

  create(address: Address, userId: number): Observable<Address> {
    const params: HttpParams = new HttpParams().set('userId', userId);
    return this.http.post<Address>(
      this.APIURL,
      address,
      {
        headers: this.getHeaders(),
        params: params
      }
    );
  }

  update(id: number, address: Address, userId: number): void {
    const params: HttpParams = new HttpParams().set('userId', userId);
    this.http.put(
      `${this.APIURL}/${id}}`,
      address,
      {
        headers: this.getHeaders(),
        params: params
      }
    );
  }

  delete(id: number, userId: number): void {
    const params: HttpParams = new HttpParams().set('userId', userId);
    this.http.delete(
      `${this.APIURL}/${id}}`,
      {
        headers: this.getHeaders(),
        params: params
      }
    );
  }

}
