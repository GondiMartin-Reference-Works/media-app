import { Component, OnInit, Input, numberAttribute } from '@angular/core';
import { Post } from '../models/post';
import { Like } from '../models/like';
import { PostService } from '../services/post.service';
import { User } from '../models/user';
import { Comment } from "../models/comment";
import { ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrl: './user-page.component.css'
})
export class UserPageComponent implements OnInit{

  @Input({transform: numberAttribute}) userId!: number;
  posts: Post[] = [];
  newComment: Comment = new Comment();

  constructor(
    private postService: PostService
  ){
    this.loadPosts();
  }

  ngOnInit(): void {
  }

  
  loadPosts() {
    // TODO: Only load posts of the user given by userId
    // See property: userId at line 16.
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

}
