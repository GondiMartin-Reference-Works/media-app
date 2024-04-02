import { User } from "./user";

export class Like {
    id: number;
    user: User;

    constructor(){
        this.id = 0;
        this.user = new User();
    }
}
