import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../models/post';
import { BaseService } from './base-service';

@Injectable({
  providedIn: 'root'
})
export class PostService extends BaseService{

  private APIURL: string = "http://localhost:8080/api/v1/post";

  constructor(
    private http: HttpClient
  ) {
    super();
  }

  create(newPost: Post): Observable<Post>{
    return this.http.post<Post>(this.APIURL, newPost, { headers: this.getHeaders()});
  }

  getAll(): Observable<Post[]>{
    return this.http.get<Post[]>(this.APIURL, { headers: this.getHeaders()});
  }
}
