import { Injectable } from "@angular/core";
import { FriendRequest } from "../models/friend-request";
import { IUserService } from "../auth/services/interfaces/iuser-service";
import { Observable } from "rxjs";
import { AuthResponse } from "../models/auth-response";
import { LoginUser } from "../models/login-user";
import { RegisterUser } from "../models/register-user";
import { UserService } from "./user.service";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Emails } from "../models/email";
import { SearchedUser } from "../models/searched-user";

@Injectable({
    providedIn: 'root'
  })
  export class FriendRequestService{
    private getRequestsURL: string = "http://localhost:8080/request/received";
    private addFriendURL: string = "http://localhost:8080/request";
    private acceptRequestURL: string = "http://localhost:8080/request/accept";
    private rejectRequestURL: string = "http://localhost:8080/request/reject";

    constructor(
      private userService: UserService,
      private http: HttpClient
      ){}

    public getFriendRequests(): Observable<FriendRequest[]>{
      this.userService.validateHeaders();
      const params = new HttpParams()
        .set('email', JSON.parse(sessionStorage.getItem('current-user-email') ?? ''));
      return this.http.get<FriendRequest[]>(this.getRequestsURL, {headers: this.userService.headers, params: params});
    }

    addFriend(toAddEmail: string): void {
      this.userService.validateHeaders();
      var request: Emails = new Emails();
      request.receiverEmail = toAddEmail;
      request.senderEmail = JSON.parse(sessionStorage.getItem('current-user-email') ?? '');
      var result = this.http.post<FriendRequest>(this.addFriendURL, request, {headers: this.userService.headers}).subscribe();
      console.log(result);
    }

    acceptRequest(toAcceptEmail: string): void {
      this.userService.validateHeaders();
      
      const receiverEmail = JSON.parse(sessionStorage.getItem('current-user-email') ?? '');
      const senderEmail = toAcceptEmail;
      const params = new HttpParams()
      .set('receiverEmail', receiverEmail)
      .set('senderEmail', senderEmail);
      var result = this.http.put<FriendRequest>(this.acceptRequestURL, {},{headers: this.userService.headers, params: params}).subscribe();
    }

    rejectRequest(toRejectEmail: string): void {
      this.userService.validateHeaders();
      
      const emails = new Emails();
      emails.receiverEmail = JSON.parse(sessionStorage.getItem('current-user-email') ?? '');
      emails.senderEmail = toRejectEmail;
      
      this.http.put(this.rejectRequestURL, emails, {headers: this.userService.headers}).subscribe();
    }
}
