import { Injectable } from '@angular/core';
import { BaseService } from './base-service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environment';
import { Group } from '../models/group';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GroupService extends BaseService{

  private APIURL: string = environment.API_URL + "/group";

  constructor(
    private http: HttpClient
  ) {
    super();
  }

  create(newGroup: Group): Observable<Group> {
    return this.http.post<Group>(
      this.APIURL,
      newGroup,
      { headers: this.getHeaders() }
    );
  }

  getAll(): Observable<Group[]> {
    return this.http.get<Group[]>(
      this.APIURL,
      { headers: this.getHeaders() }
    );
  }

  getAllByUserId(userId: number): Observable<Group[]> {
    const params: HttpParams = new HttpParams().set('userId', userId);
    return this.http.get<Group[]>(
      `${this.APIURL}/my`,
      { 
        headers: this.getHeaders(),
        params: params
      }
    );
  }

  getById(groupId: number): Observable<Group> {
    return this.http.get<Group>(
      `${this.APIURL}/${groupId}`,
      { headers: this.getHeaders() }
    );
  }

  update(group: Group): Observable<Group> {
    return this.http.put<Group>(
      `${this.APIURL}/${group.id}`,
      group,
      { headers: this.getHeaders() }
    );
  }

  delete(groupId: number): Observable<void> {
    return this.http.delete<void>(
      `${this.APIURL}/${groupId}`,
      { headers: this.getHeaders() }
    );
  }
}
