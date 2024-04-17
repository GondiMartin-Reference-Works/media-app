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

  likePost(postId: number, userId: number): Observable<Post>{
    return this.http.post<Post>(
      `${this.APIURL}/${postId}/like`,
      userId,
      { headers: this.getHeaders()})
  }

  unLikePost(postId: number, userId: number): Observable<Post>{
    return this.http.post<Post>(
      `${this.APIURL}/${postId}/unlike`,
      userId,
      { headers: this.getHeaders()});
  }

  likeComment(postId: number, commentId: number, userId: number): Observable<Post>{
    return this.http.post<Post>(
      `${this.APIURL}/${postId}/comment/${commentId}/like`,
      userId,
      { headers: this.getHeaders()});
  }

  unlikeComment(postId: number, commentId: number, userId: number): Observable<Post>{
    return this.http.post<Post>(
      `${this.APIURL}/${postId}/comment/${commentId}/unlike`,
      userId,
      { headers: this.getHeaders()});
  }

  deleteComment(postId: number, commentId: number, userId: number): Observable<void>{
    return this.http.delete<void>(
      `${this.APIURL}/${postId}/comment/${commentId}`,
      { headers: this.getHeaders(),
        body: userId
      });
  }
}
