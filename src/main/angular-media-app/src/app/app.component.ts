import { Component, OnInit} from '@angular/core';
import { UserService } from './services/user.service';
import { AuthService } from './auth/services/auth.service';
import { SearchedUser } from './models/searched-user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'angular-media-app';
  windowOpen: boolean = false;

  users: SearchedUser[] = [];
  filteredUsers: SearchedUser[] = [];

  constructor(
    public userService: UserService,
    public authService: AuthService){
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
      this.filteredUsers = this.users;
      this.windowOpen = false;
      return;
    }

    this.windowOpen = true;

    this.filteredUsers = this.users.filter(
      user => {
        let name = user.firstName + " " + user.lastName;
        name.includes(text);
      }
    )

    console.log(this.users);
    //console.log(this.filteredUsers);
  }
}
