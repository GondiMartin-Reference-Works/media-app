import { User } from "./user";

export class AuthResponse {
    token: string;
    user: User;

    constructor(){
        this.token = "";
        this.user = new User();
    }
}
