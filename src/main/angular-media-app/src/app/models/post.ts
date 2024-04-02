import { User } from "./user";
import { Group } from "./group";
import { Like } from "./like";
import { Comment } from "./comment";

export class Post {
    user: User | null;
    group: Group | null;
    text: string;
    image: Int8Array | null;
    likes: Like[];
    comments: Comment[];

    constructor(){
        this.user = new User();
        this.group = new Group();
        this.text = "";
        this.image = new Int8Array();
        this.likes = [];
        this.comments = [];
    }
}
