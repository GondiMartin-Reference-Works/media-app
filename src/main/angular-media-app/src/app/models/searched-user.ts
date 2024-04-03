export class SearchedUser {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    friend: boolean;

    constructor(){
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.friend = false;
    }
}
