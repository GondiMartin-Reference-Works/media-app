import { User } from "./user";
import { Group } from "./group";
import { Like } from "./like";
import { Comment } from "./comment";

export class Post {
    user: User | null;
    group: Group | null;
    text: string;
    image: Blob | null;
    likes: Like[];
    comments: Comment[];

    constructor(){
        this.user = new User();
        this.group = new Group();
        this.text = "";
        this.image = null;
        this.likes = [];
        this.comments = [];
    }
}
