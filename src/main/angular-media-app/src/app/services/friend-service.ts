import { Injectable } from "@angular/core";
import { FriendListElement } from "../models/friend-list-element";
import { Observable } from "rxjs/internal/Observable";
import { UserService } from "./user.service";
import { HttpClient, HttpParams } from "@angular/common/http";

@Injectable({
    providedIn: 'root'
  })
  export class FriendService{

    private getFriendsURL = "http://localhost:8080/friend";
    private deleteFriendsURL = "http://localhost:8080/friend";

    constructor(
        private userService: UserService,
        private http: HttpClient
    ){}

    public getFriends(): Observable<FriendListElement[]>{
        this.userService.validateHeaders();

        const email = JSON.parse(sessionStorage.getItem('current-user-email') ?? '');
        const params = new HttpParams()
        .set('email', email);
        return this.http.get<FriendListElement[]>(this.getFriendsURL, {headers: this.userService.headers, params: params});
    }

    public deleteFriend(email: string): void{
        this.userService.validateHeaders();
        
        const loggedInUserEmail = JSON.parse(sessionStorage.getItem('current-user-email') ?? '');
        const toBeDeletedUserEmail = email;
        const params = new HttpParams()
        .set('loggedInUserEmail', loggedInUserEmail)
        .set('toBeDeletedUserEmail', toBeDeletedUserEmail);
        
            
        this.http.delete(this.deleteFriendsURL, {headers: this.userService.headers, params: params}).subscribe();
    }
  }