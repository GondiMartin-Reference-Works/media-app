import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../models/post';
import { BaseService } from './base-service';
import { environment } from '../../environment';
import { Like } from '../models/like';
import { Comment } from '../models/comment';

@Injectable({
  providedIn: 'root'
})
export class PostService extends BaseService{

  private APIURL: string = environment.API_URL + "/post";

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

  getById(postId: number): Observable<Post>{
    return this.http.get<Post>(`${this.APIURL}/findone/${postId}`, { headers: this.getHeaders()});
  }

  getAllById(userId: number): Observable<Post[]>{
    return this.http.get<Post[]>(`${this.APIURL}/${userId}`, { headers: this.getHeaders()});
  }

  likePost(postId: number, userId: number): Observable<Like[]>{
    return this.http.post<Like[]>(
      `${this.APIURL}/${postId}/like`,
      userId,
      { headers: this.getHeaders()})
  }

  unLikePost(postId: number, userId: number): Observable<Like[]>{
    return this.http.post<Like[]>(
      `${this.APIURL}/${postId}/unlike`,
      userId,
      { headers: this.getHeaders()});
  }

  likeComment(postId: number, commentId: number, userId: number): Observable<Comment[]>{
    return this.http.post<Comment[]>(
      `${this.APIURL}/${postId}/comment/${commentId}/like`,
      userId,
      { headers: this.getHeaders()});
  }

  unlikeComment(postId: number, commentId: number, userId: number): Observable<Comment[]>{
    return this.http.post<Comment[]>(
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

  createComment(postId: number, newComment: Comment): Observable<Comment>{
    return this.http.post<Comment>(
      `${this.APIURL}/${postId}/comment`,
      newComment,
      { headers: this.getHeaders()});
  }

  deletePost(postId: number): Observable<void>{
    return this.http.delete<void>(
      `${this.APIURL}/${postId}`,
      { headers: this.getHeaders()});
  }

  updatePost(post: Post): Observable<Post>{
    return this.http.put<Post>(
      `${this.APIURL}/${post.id}`,
      post,
      { headers: this.getHeaders()}
    );
  }
}
