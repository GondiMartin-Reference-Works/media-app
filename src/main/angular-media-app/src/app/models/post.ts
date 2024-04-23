import { User } from "./user";
import { Group } from "./group";
import { Like } from "./like";
import { Comment } from "./comment";

export class Post {
    id: number;
    user: User | null;
    group: Group | null;
    text: string;
    image: number[] | null;
    likes: Like[];
    comments: Comment[];
    imgSrc: string;
    isCommenting: boolean;

    constructor(){
        this.id = 0;
        this.user = new User();
        this.group = new Group();
        this.text = "";
        this.image = null;
        this.likes = [];
        this.comments = [];
        this.imgSrc = '';
        this.isCommenting = false;
    }

    static convertNewPost(post: Post): Post{
        let newPost: Post = new Post();
        newPost.id = post.id;
        newPost.user = post.user;
        newPost.group = post.group;
        newPost.text = post.text;
        newPost.image = post.image;
        newPost.likes = post.likes;
        newPost.comments = post.comments;
        newPost.imgSrc = newPost.getImageSrc();
        newPost.isCommenting = false;
        return newPost;
    }

    getImageSrc(): string{
        if(this.image){
            return 'data:image/jpeg;base64,' + this.image;
        }
        return '';
    }
}
