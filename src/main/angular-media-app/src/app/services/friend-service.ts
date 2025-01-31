import { Injectable } from "@angular/core";
import { FriendListElement } from "../models/friend-list-element";
import { Observable } from "rxjs/internal/Observable";
import { HttpClient, HttpParams } from "@angular/common/http";
import {BaseService} from "./base-service";
import { environment } from "../../environment";

@Injectable({
    providedIn: 'root'
  })
  export class FriendService extends BaseService{

    private getFriendsURL = environment.API_URL + "/friend";
    private deleteFriendsURL = environment.API_URL + "/friend";

    constructor(
        private http: HttpClient
    ){
      super();
    }

    public getFriends(): Observable<FriendListElement[]>{
        const email = JSON.parse(sessionStorage.getItem('current-user-email') ?? '');
        const params = new HttpParams()
        .set('email', email);
        return this.http.get<FriendListElement[]>(this.getFriendsURL, {headers: this.getHeaders(), params: params});
    }

    public deleteFriend(email: string): void{
        const loggedInUserEmail = JSON.parse(sessionStorage.getItem('current-user-email') ?? '');
        const toBeDeletedUserEmail = email;
        const params = new HttpParams()
        .set('loggedInUserEmail', loggedInUserEmail)
        .set('toBeDeletedUserEmail', toBeDeletedUserEmail);


        this.http.delete(this.deleteFriendsURL, {headers: this.getHeaders(), params: params}).subscribe();
    }
  }
