import { Component, OnInit } from '@angular/core';
import {  FriendListElement } from '../models/friend-list-element';
import { FriendRequestService } from '../services/friend-request.service';
import { AuthService } from '../auth/services/auth.service';
import { FriendService } from '../services/friend-service';

@Component({
  selector: 'app-manage-friends',
  templateUrl: './manage-friends.component.html',
  styleUrl: './manage-friends.component.css'
})
export class ManageFriendsComponent implements OnInit{
  public connections: FriendListElement[] = [];

  constructor(
    public friendService: FriendService,
    public authService: AuthService
  ){}

  ngOnInit(): void {
    if(this.authService.isLoggedIn()){
      this.friendService.getFriends().subscribe(friends => this.connections = friends);
    }
  }
}
