export class RegisterUser {
    firstName: string;
    lastName: string;
    birthDate?: Date | null;
    email: string;
    password: string;

    constructor(){
        this.firstName = "";
        this.lastName = "";
        this.birthDate = new Date("2001-01-01");
        this.email = "";
        this.password = "";
    }
}
