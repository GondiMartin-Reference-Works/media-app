import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
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
}
