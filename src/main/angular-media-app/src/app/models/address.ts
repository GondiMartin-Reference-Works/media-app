export class Address {
    id: number;
    country: string;
    city: string;
    zipCode: string;
    street: string;
    houseNum: string;
    isEditing: boolean;

    constructor() {
        this.id = 0;
        this.country = "";
        this.city = "";
        this.zipCode = "";
        this.street = "";
        this.houseNum = "";
        this.isEditing = false;
    }

    
}
