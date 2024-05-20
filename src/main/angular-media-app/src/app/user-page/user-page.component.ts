import { Component, OnInit, Input, numberAttribute, NgModule } from '@angular/core';
import { Post } from '../models/post';
import { Like } from '../models/like';
import { PostService } from '../services/post.service';
import { User } from '../models/user';
import { Comment } from "../models/comment";
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrl: './user-page.component.css'
})
export class UserPageComponent implements OnInit{

  @Input({
    transform: numberAttribute,
  }) userId: number;
  posts: Post[] = [];
  newComment: Comment = new Comment();
  uiEditMode: boolean = false;
  postToBeEdited: Post = new Post();
  selectedFile: string = '';


  constructor(
    private postService: PostService,
    private route: ActivatedRoute
  ){
    this.userId = +this.route.snapshot.params["userId"];
    this.loadPosts();
  }

  ngOnInit(): void {
  }

  isEditing(post: Post): boolean{
    return post.isEditing;
  }

  isMyPage(): boolean{
    const loggedInUser: number = JSON.parse(sessionStorage.getItem('current-user') ?? '').id;
    return loggedInUser === this.userId;
  }

  deletePost(post: Post){
    this.postService.deletePost(post.id).subscribe(_ => {
      const index = this.posts.findIndex(p => p.id === post.id);
      this.posts.splice(index, 1);
      this.ngOnInit();
    });
  }

  editPost(post: Post){
    if(post.isEditing){
      post.isEditing = false;
      this.uiEditMode = false;
    }
    else if(!post.isEditing && !this.uiEditMode){
      post.isEditing = true;
      this.uiEditMode = true;
      this.postToBeEdited = Object.assign(new Post(), post);
    }
    this.ngOnInit();
  }

  updatePost(){
    this.postService.updatePost(this.postToBeEdited).subscribe(_ => {
      this.postService.getById(this.postToBeEdited.id).subscribe(post => {
      
        const index = this.posts.findIndex(p => p.id === this.postToBeEdited.id);
        this.editPost(this.posts[index]);
        this.posts[index] = Post.convertNewPost(post);
        this.posts[index].imgSrc = post.getImageSrc()
      })
    })

    this.ngOnInit();
  }

  removeImage(post: Post){
    this.postToBeEdited.image = null;
  }

  loadPosts() {
    this.postService.getAllById(this.userId).subscribe(posts => {
      this.posts = posts.map(postData => {
        let post = Object.assign(new Post(), postData);
        post.imgSrc = post.getImageSrc();
        post.comments = post.comments.reverse();
        return post;
      });
    });
  }

  likePost(post: Post) {
    const currentUser: User = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    this.postService.likePost(post.id, currentUser.id).subscribe(likes => {
      const index = this.posts.findIndex(p => p.id === post.id);
      this.posts[index].likes = likes;
      this.ngOnInit();
    });
  }

  unlikePost(post: Post) {
    const currentUser: User = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    this.postService.unLikePost(post.id, currentUser.id).subscribe(likes => {
      const index = this.posts.findIndex(p => p.id === post.id);
      this.posts[index].likes = likes;
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

  likeComment(comment: Comment){
    const currentUser: User = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    const post = this.posts.find(p =>
      p.comments.find(c => c.id === comment.id)
    );
    if (post){
      this.postService.likeComment(post.id, comment.id, currentUser.id).subscribe(comments => {
        const index = this.posts.findIndex(p => p.id === post.id);
        this.posts[index].comments = comments.reverse();
        this.ngOnInit();
      });
    }
  }

  unlikeComment(comment: Comment){
    const currentUser: User = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    const post = this.posts.find(p =>
      p.comments.find(c => c.id === comment.id)
    );
    if (post){
      this.postService.unlikeComment(post.id, comment.id, currentUser.id).subscribe(comments => {
        const index = this.posts.findIndex(p => p.id === post.id);
        this.posts[index].comments = comments.reverse();
        this.ngOnInit();
      });
    }
  }

  deleteComment(comment: Comment){
    const currentUser: User = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    const post = this.posts.find(p =>
      p.comments.find(c => c.id === comment.id)
    );
    if (post){
      this.postService.deleteComment(post.id, comment.id, currentUser.id).subscribe(_ => {
        const postIndex = this.posts.findIndex(p => p.id === post.id);
        const commentIndex = this.posts[postIndex].comments.findIndex(c => c.id === comment.id);
        this.posts[postIndex].comments.splice(commentIndex, 1);
        this.ngOnInit();
      });
    }
  }

  commentToggle(post: Post): void{
    post.isCommenting = !post.isCommenting;
    if (post.isCommenting)
      this.newComment = new Comment();
    this.ngOnInit();
  }

  isCurrentUserComment(comment: Comment){
    const currentUser: User = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    return currentUser.id === comment.user.id;
  }

  isCommenting(post: Post): boolean{
    return post.isCommenting;
  }

  comment(post: Post){
    this.newComment.user = JSON.parse(sessionStorage.getItem('current-user') ?? '');
    this.newComment.likes = [];

    this.postService.createComment(post.id, this.newComment).subscribe(comment => {
      const postIndex = this.posts.findIndex(p => p.id === post.id);
      this.posts[postIndex].comments.unshift(comment);
      this.newComment = new Comment();
      this.ngOnInit();
    });
  }

  onFileNameChanged(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file.name;
    }
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = (e: any) => {
        const arrayBuffer: ArrayBuffer = e.target.result;
        const byteArray = new Uint8Array(arrayBuffer);
        this.postToBeEdited.image = Array.from(byteArray);
      };

      reader.readAsArrayBuffer(file);
    }
  }

}
