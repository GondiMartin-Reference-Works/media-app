import { Like } from "./like";
import { User } from "./user";

export class Comment {
    id: number;
    text: string;
    user: User;
    likes: Like[];

    constructor() {
        this.id = 0;
        this.text = "";
        this.user = new User();
        this.likes = [];
    }
}
