import { Address } from "./address";

export class User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    birthDate: Date | null;
    profilePicture: number[] | null;
    imgSrc: string;
    addresses: Address[];

    constructor(){
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.birthDate = null;
        this.profilePicture = null;
        this.imgSrc = '';
        this.addresses = [];
    }

    static convertNewUser(user: User | null): User {
        if(user === null){
            return new User();
        }
        let newUser: User = new User();
        newUser.id = user.id;
        newUser.firstName = user.firstName;
        newUser.lastName = user.lastName;
        newUser.email = user.email;
        newUser.birthDate = user.birthDate;
        newUser.profilePicture = user.profilePicture;
        newUser.imgSrc = newUser.getImageSrc();
        newUser.addresses = user.addresses;
        return newUser;
    }

    getImageSrc(): string {
        if (this.profilePicture){
            return 'data:image/jpeg;base64,' + this.profilePicture;
        }
        return '';
    }
}
