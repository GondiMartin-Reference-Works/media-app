import { Component, OnInit} from '@angular/core';
import { UserService } from './services/user.service';
import { AuthService } from './auth/services/auth.service';
import { SearchedUser } from './models/searched-user';
import { FriendRequestService } from './services/friend-request.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'angular-media-app';
  term: string = "";

  users: SearchedUser[] = [];
  filteredUsers: SearchedUser[] = [];

  constructor(
    public userService: UserService,
    public authService: AuthService,
    public requestService: FriendRequestService){
      this.filteredUsers = [];
      if (this.authService.isLoggedIn()){
        this.getAllUser();
      }
  }

  ngOnInit(): void {
  }

  getAllUser(){
    this.userService.getAll().subscribe((users) => {
      this.users = users;
      
    });
  }

  filterResults(text: string){
    if (!text) {
      this.filteredUsers = [];
      return;
    }

    if (text === ""){
      this.filteredUsers = [];
      return;
    }

    console.log(this.users);
    this.filteredUsers = this.users.filter(
      user => {
        let name = user.firstName.toLowerCase() + " " + user.lastName.toLowerCase();
        return name.includes(text.toLowerCase());
      }
    )
    .filter(user => user.email != JSON.parse(sessionStorage.getItem('current-user-email') ?? ''))
  }
}
