import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/services/auth.service';
import { FriendRequest } from '../models/friend-request';
import { FriendRequestService } from '../services/friend-request.service';

@Component({
  selector: 'app-friend-request',
  templateUrl: './friend-request.component.html',
  styleUrl: './friend-request.component.css'
})
export class FriendRequestComponent implements OnInit{
  public requests: FriendRequest[] = [];

  constructor(
    public requestService: FriendRequestService,
    public authService: AuthService
  ){}

  ngOnInit(){
    this.requestService.getFriendRequests().subscribe(request => {
      this.requests = request;
      console.log(request);
    });
  }

  acceptRequest(receiverEmail: string): void{
    this.requestService.acceptRequest(receiverEmail);
    window.location.reload();
  }

  rejectRequest(receiverEmail: string): void{
    this.requestService.rejectRequest(receiverEmail);
    window.location.reload();
  }
}
