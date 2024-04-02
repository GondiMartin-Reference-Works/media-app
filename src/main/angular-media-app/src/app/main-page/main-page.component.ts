import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { AuthService } from '../auth/services/auth.service';
import { Post } from '../models/post';
import { PostService } from '../services/post.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent implements OnInit{

  createFormButton: boolean;
  posts: Post[] = [];
  newPost: Post = new Post();

  constructor(
    private appComponent: AppComponent,
    private authService: AuthService,
    private postService: PostService) {
      this.createFormButton = false;
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()){
      this.loadPosts();
      this.appComponent.getAllUser();
    }
  }

  // Shows a brand-new form
  createForm(){
    this.createFormButton = true;
    this.newPost = new Post();
  }

  // Validates + sends + hides the form
  sendForm(){
    const json = sessionStorage.getItem("current-user");
    if (json != null){
      const user = JSON.parse(json);
      this.newPost.user = user;
      this.newPost.group = null;
      this.newPost.likes = [];
      this.newPost.comments = [];
      this.newPost.image = null; // TODO: Image deszerializáció
      this.postService.create(this.newPost).subscribe(_ => {
        this.createFormButton = false;
        window.location.reload();
      });
    }
    
  }

  // Hides the form
  closeForm(){
    this.createFormButton = false;
  }

  loadPosts(){
    this.postService.getAll().subscribe(posts =>{
      this.posts = posts;
    });
  }

}
