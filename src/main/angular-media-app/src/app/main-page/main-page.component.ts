import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { AuthService } from '../auth/services/auth.service';
import { Post } from '../models/post';
import { PostService } from '../services/post.service';
import { Like } from '../models/like';
import { User } from '../models/user';
import { Comment } from "../models/comment";
import { ReloadService } from '../services/reload.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent implements OnInit {

  createFormButton: boolean;
  posts: Post[] = [];
  newPost: Post = new Post();
  selectedFile: string = "";

  constructor(
    private appComponent: AppComponent,
    private authService: AuthService,
    private postService: PostService
  ) {
    this.createFormButton = false;
    if (this.authService.isLoggedIn()) {
      this.loadPosts();
    }
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.appComponent.getAllUser();
    }
  }

  // Shows a brand-new form
  createForm() {
    this.createFormButton = true;
    this.newPost = new Post();
  }

  // Validates + sends + hides the form
  sendForm() {
    const json = sessionStorage.getItem("current-user");
    if (json != null) {
      this.newPost.user = JSON.parse(json);
      this.newPost.group = null;
      this.newPost.likes = [];
      this.newPost.comments = [];
      this.postService.create(this.newPost).subscribe(_ => {
        this.createFormButton = false;
        this.ngOnInit();
      });
    }

  }

  onFileSelected(event: any): void {
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
  closeForm() {
    this.createFormButton = false;
    this.newPost = new Post();
    this.selectedFile = "";
  }

  loadPosts() {
    this.postService.getAll().subscribe(posts => {
      this.posts = posts.map(postData => {
        let post = Object.assign(new Post(), postData);
        post.imgSrc = post.getImageSrc();
        return post;
      });
    });
  }

  onFileNameChanged(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file.name;
    }
  }

  likePost(post: Post) {
    const currentUser: User = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    this.postService.likePost(post.id, currentUser.id).subscribe(mp => {
      const index = this.posts.findIndex(p => p.id === post.id);
      const imgSrc = this.posts[index].imgSrc;
      this.posts[index] = mp;
      this.posts[index].imgSrc = imgSrc;
      this.ngOnInit();
    });
  }

  unlikePost(post: Post) {
    const currentUser: User = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    this.postService.unLikePost(post.id, currentUser.id).subscribe(mp => {
      const index = this.posts.findIndex(p => p.id === post.id);
      const imgSrc = this.posts[index].imgSrc;
      this.posts[index] = mp;
      this.posts[index].imgSrc = imgSrc;
      this.ngOnInit();
    });
  }

  isLikedByUser(post: Post): boolean {
    const currentUser = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    let like: Like | undefined = post.likes.find(l => l.user.id === currentUser.id);
    return !!like;
  }

  isCommentLikedByUser(comment: Comment): boolean {
    const currentUser = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    let like: Like | undefined = comment.likes.find(l => l.user.id === currentUser.id);
    return !!like;
  }
}
