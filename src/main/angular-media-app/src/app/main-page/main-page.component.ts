import {Component, OnInit} from '@angular/core';
import {AppComponent} from '../app.component';
import {AuthService} from '../auth/services/auth.service';
import {Post} from '../models/post';
import {PostService} from '../services/post.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent implements OnInit{

  createFormButton: boolean;
  posts: Post[] = [];
  newPost: Post = new Post();
  selectedFile: string = "";

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
      this.newPost.user = JSON.parse(json);
      this.newPost.group = null;
      this.newPost.likes = [];
      this.newPost.comments = [];
      this.postService.create(this.newPost).subscribe(_ => {
        this.createFormButton = false;
        window.location.reload();
      });
    }

  }

  onFileSelected(event: any): void{
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = (e: any) => {
        const arrayBuffer: ArrayBuffer = e.target.result;
        const byteArray = new Uint8Array(arrayBuffer);
        this.newPost.image = Array.from(byteArray);
      };

      reader.readAsArrayBuffer(file);
    }
  }

  // Hides the form
  closeForm(){
    this.createFormButton = false;
    this.newPost = new Post();
    this.selectedFile = "";
  }

  loadPosts(){
    this.postService.getAll().subscribe(posts =>{
      this.posts = posts.map(postData => Object.assign(new Post(), postData));
    });

  }

  onFileNameChanged(event: any){
    const file: File = event.target.files[0];
    if(file){
      this.selectedFile = file.name;
    }
  }

}
