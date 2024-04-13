import { Injectable } from "@angular/core";
import { FriendRequest } from "../models/friend-request";
import { Observable } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Emails } from "../models/email";
import {BaseService} from "./base-service";
import { environment } from "../../environment";

@Injectable({
    providedIn: 'root'
  })
  export class FriendRequestService extends BaseService{
    private getRequestsURL: string = environment.API_URL + "/request/received";
    private addFriendURL: string = environment.API_URL + "/request";
    private acceptRequestURL: string = environment.API_URL + "/request/accept";
    private rejectRequestURL: string = environment.API_URL + "/request/reject";

    constructor(
      private http: HttpClient
      ){
      super();
    }

    public getFriendRequests(): Observable<FriendRequest[]>{
      const params = new HttpParams()
        .set('email', JSON.parse(sessionStorage.getItem('current-user-email') ?? ''));
      return this.http.get<FriendRequest[]>(this.getRequestsURL, {headers: this.getHeaders(), params: params});
    }

    addFriend(toAddEmail: string): void {
      var request: Emails = new Emails();
      request.receiverEmail = toAddEmail;
      request.senderEmail = JSON.parse(sessionStorage.getItem('current-user-email') ?? '');
      var result = this.http.post<FriendRequest>(this.addFriendURL, request, {headers: this.getHeaders()}).subscribe();
      console.log(result);
    }

    acceptRequest(toAcceptEmail: string): void {
      const receiverEmail = JSON.parse(sessionStorage.getItem('current-user-email') ?? '');
      const senderEmail = toAcceptEmail;
      const params = new HttpParams()
      .set('receiverEmail', receiverEmail)
      .set('senderEmail', senderEmail);
      var result = this.http.put<FriendRequest>(this.acceptRequestURL, {},{headers: this.getHeaders(), params: params}).subscribe();
    }

    rejectRequest(toRejectEmail: string): void {
      const emails = new Emails();
      emails.receiverEmail = JSON.parse(sessionStorage.getItem('current-user-email') ?? '');
      emails.senderEmail = toRejectEmail;

      this.http.put(this.rejectRequestURL, emails, {headers: this.getHeaders()}).subscribe();
    }
}
