import { Injectable } from "@angular/core";
import { IUserService } from "../interfaces/iuser-service";
import { RegisterUser } from "../../models/register-user";
import { Observable, of } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserService implements IUserService{
    
    private mockUsers: RegisterUser[] = [
        {
            firstName: "Martin",
            lastName: "Göndöcs",
            birthDate: null,
            email: "martin@youtellme.hu",
            password: "123"
        },
        {
            firstName: "Vilmos",
            lastName: "Rideg",
            birthDate: null,
            email: "vilmos@youtellme.hu",
            password: "456"
        }
    ];

    getAll(): Observable<RegisterUser[]> {
        return of(this.mockUsers);
    }

    create(newUser: RegisterUser): Observable<RegisterUser> {
        this.mockUsers.push({
            ...newUser
        });
        return of(newUser);
    }
}