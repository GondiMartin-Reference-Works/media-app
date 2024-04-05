import { User } from "./user";

export class Comment {
    id: number;
    text: string;
    user: User;

    constructor() {
        this.id = 0;
        this.text = "";
        this.user = new User();
    }
}
